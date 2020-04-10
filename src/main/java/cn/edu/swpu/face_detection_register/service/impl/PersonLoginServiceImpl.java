package cn.edu.swpu.face_detection_register.service.impl;

import cn.edu.swpu.face_detection_register.dao.UserInfoMapper;
import cn.edu.swpu.face_detection_register.exception.SystemException;
import cn.edu.swpu.face_detection_register.model.bo.UserInfo;
import cn.edu.swpu.face_detection_register.model.dto.FaceRequestParam;
import cn.edu.swpu.face_detection_register.model.dto.RegisterRequestParam;
import cn.edu.swpu.face_detection_register.model.dto.VerifyUserNameParam;
import cn.edu.swpu.face_detection_register.model.enums.ExceptionInfoEnum;
import cn.edu.swpu.face_detection_register.model.enums.ImageTypeEnum;
import cn.edu.swpu.face_detection_register.model.vo.*;
import cn.edu.swpu.face_detection_register.service.IFaceDetectionService;
import cn.edu.swpu.face_detection_register.service.IPersonLoginService;
import cn.edu.swpu.face_detection_register.util.JWTUtil;
import cn.edu.swpu.face_detection_register.util.ResponseVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PersonLoginServiceImpl implements IPersonLoginService {

    @Autowired
    private IFaceDetectionService faceDetectionService;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Value("${jwt.secret:#{'202045'}}")
    private String secret;

    private String faceFeild = "age,beauty,expression,face_shape,gender,glasses,landmark,landmark150,race,quality,eye_status,emotion,face_type,mask";

    private ResponseVo<DetectFaceVo> getFaceField(String base64Image,String faceFeild) {
        FaceRequestParam faceRequestParam = new FaceRequestParam();
        faceRequestParam.setImage(base64Image);
        faceRequestParam.setImageType(ImageTypeEnum.BASE64.getImageType());
        faceRequestParam.setFaceField(faceFeild);
        ResponseVo<DetectFaceVo> detectFaceVoResponseVo = faceDetectionService.detectFace(faceRequestParam);
        if (detectFaceVoResponseVo == null) {
            throw new SystemException(ExceptionInfoEnum.REQUEST_INTERFACE_EXCEPTIO,false);
        }else if (!"0".equals(detectFaceVoResponseVo.getErrorCode()) || detectFaceVoResponseVo.getResult() == null){
            String erroMsg = detectFaceVoResponseVo.getErrorMsg() == null? ExceptionInfoEnum.REQUEST_INTERFACE_EXCEPTIO.getErroMsg():detectFaceVoResponseVo.getErrorMsg();
            throw new SystemException(ExceptionInfoEnum.REQUEST_INTERFACE_EXCEPTIO.getErroCode(),erroMsg,false);
        }
        return detectFaceVoResponseVo;
    }

    private FaceRequestParam getSearchFaceRequestParam() {
        FaceRequestParam faceRequestParam = new FaceRequestParam();
        faceRequestParam.setImageType(ImageTypeEnum.BASE64.getImageType());
        faceRequestParam.setGroupIdList("201912311715");
        faceRequestParam.setQualityControl("HIGH");
        faceRequestParam.setLivenessControl("HIGH");
        return faceRequestParam;
    }

    private  ResponseVo<SearchFaceVo> getSearchFaceResponseVo(String base64Image){
        //调用查询接口，确认用户是否已经注册
        FaceRequestParam faceRequestParam = getSearchFaceRequestParam();
        faceRequestParam.setImage(base64Image);
        ResponseVo<SearchFaceVo> searchFaceVoResponseVo = faceDetectionService.searchFace(faceRequestParam);
        if (searchFaceVoResponseVo == null) {
            throw new SystemException(ExceptionInfoEnum.REQUEST_INTERFACE_EXCEPTIO,false);
        }
        if (!"0".equals(searchFaceVoResponseVo.getErrorCode()) || searchFaceVoResponseVo.getResult() == null){
            throw new SystemException(ExceptionInfoEnum.REQUEST_INTERFACE_EXCEPTIO.getErroCode(),searchFaceVoResponseVo.getErrorMsg(),false);
        }
        return searchFaceVoResponseVo;
    }
    @Override
    public ResponseVo<Boolean> personRegister(RegisterRequestParam registerRequestParam) {
        //调用查询接口，确认用户是否已经注册
        ResponseVo<SearchFaceVo> searchFaceVoResponseVo = getSearchFaceResponseVo(registerRequestParam.getBase64Image());
        List<UserListVo> userListVos = searchFaceVoResponseVo.getResult().getUserList();
        if (userListVos != null && userListVos.size() != 0 && userListVos.get(0).getScore() > 80) {
            //已经注册
            throw new SystemException(ExceptionInfoEnum.REPEAT_REGISTER,false);
        }
        //调用添加用户的接口
        String userId = UUID.randomUUID().toString().replace("-","");
        FaceRequestParam addFaceRequestParam = new FaceRequestParam();
        addFaceRequestParam.setImage(registerRequestParam.getBase64Image());
        addFaceRequestParam.setImageType(ImageTypeEnum.BASE64.getImageType());
        addFaceRequestParam.setGroupId("201912311715");
        addFaceRequestParam.setUserId(userId);
        addFaceRequestParam.setUserInfo(registerRequestParam.getUserName());
        addFaceRequestParam.setQualityControl("HIGH");
        addFaceRequestParam.setLivenessControl("HIGH");
        addFaceRequestParam.setActionType("REPLACE");
        ResponseVo<FaceRegisterVo> faceRegisterVoResponseVo = faceDetectionService.registerFace(addFaceRequestParam);
        if (faceRegisterVoResponseVo == null) {
            throw new SystemException(ExceptionInfoEnum.REQUEST_INTERFACE_EXCEPTIO,false);
        }
        //在用户表中插入数据
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserName(registerRequestParam.getUserName());
        String faceToken = faceRegisterVoResponseVo.getResult().getFaceToken();
        userInfo.setFaceToken(faceToken);
        //判断是否已经注册，避免重复注册
        UserInfo userInfoSelect = userInfoMapper.selectByPrimaryKey(userId);
        if (userInfoSelect != null) {
            throw new SystemException(ExceptionInfoEnum.REPEAT_REGISTER,false);
        }
        //用户表出入数据
        int count = userInfoMapper.insert(userInfo);
        if (count < 1) {
            throw new SystemException(ExceptionInfoEnum.USERINFO_INSERT_EXCEPTION,false);
        }
        return ResponseVoUtil.success(true);
    }

    @Override
    public ResponseVo<Boolean> verifyBase64Image(String base64Image) {
        //调用人脸属性检测接口，判断人脸遮挡，眼镜是否睁开，是否戴眼镜，是否戴口罩
        ResponseVo<DetectFaceVo> detectFaceVoResponseVo = getFaceField(base64Image,faceFeild);
        DetectFaceVo detectFaceVo = detectFaceVoResponseVo.getResult();
        //目前只支持一个人脸信息的采集
        if (detectFaceVo.getFaceNum() != detectFaceVo.getFaceList().size() || detectFaceVo.getFaceNum() >1 ) {
            throw new SystemException(ExceptionInfoEnum.FACE_FIELD_ONLY_ONE,false);
        }
        DetectFacePartVo detectFacePartVo = detectFaceVo.getFaceList().get(0);

        if ("0".equals(String.valueOf(detectFacePartVo.getQuality().get("completeness")))) {
            throw new SystemException(ExceptionInfoEnum.FACE_FEILD_COMPLETENESS,false);
        }
        if (detectFacePartVo.getEyeStatus().get("left_eye") != 1 || detectFacePartVo.getEyeStatus().get("right_eye") != 1) {
            throw new SystemException(ExceptionInfoEnum.FACE_FEILD_EYE_CLOSE,false);
        }
        if ("sun".equals(detectFacePartVo.getGlasses().getType())) {
            throw new SystemException(ExceptionInfoEnum.FACE_FEILD_WEAR_GLASSES,false);
        }
        if ("1".equals(detectFacePartVo.getMask().getType())) {
            throw new SystemException(ExceptionInfoEnum.FACE_FEILD_WEAR_MASK,false);
        }
        return ResponseVoUtil.success(true);
    }

    @Override
    public ResponseVo<String> login(String base64Image) {
        //获取到userId
        ResponseVo<SearchFaceVo> searchFaceResponseVo = getSearchFaceResponseVo(base64Image);
        //查数据库
        List<UserListVo> userListVos = searchFaceResponseVo.getResult().getUserList();
        if (userListVos == null || userListVos.size() < 1 || userListVos.get(0).getScore() <= 80) {
            throw new SystemException(ExceptionInfoEnum.NOT_REGISTER,null);
        }
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userListVos.get(0).getUserId());
        if (userInfo == null) {
            throw new SystemException(ExceptionInfoEnum.NOT_REGISTER,null);
        }
        //颁发token
        String token = JWTUtil.createToken(userInfo.getUserId(), secret);
        return ResponseVoUtil.success(token);
    }

    @Override
    public ResponseVo<Boolean> verifyUserName(VerifyUserNameParam verifyUserNameParam) {
        List<UserInfo> userInfos = userInfoMapper.selectByUserName(verifyUserNameParam.getUserName());
        if (userInfos != null && userInfos.size() >0) {
            throw new SystemException(ExceptionInfoEnum.REPEAT_USER_NAME,false);
        }
        return ResponseVoUtil.success(true);
    }
}
