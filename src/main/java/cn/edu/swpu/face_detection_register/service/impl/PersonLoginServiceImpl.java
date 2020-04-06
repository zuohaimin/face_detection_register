package cn.edu.swpu.face_detection_register.service.impl;

import cn.edu.swpu.face_detection_register.dao.UserInfoMapper;
import cn.edu.swpu.face_detection_register.exception.SystemException;
import cn.edu.swpu.face_detection_register.model.bo.UserInfo;
import cn.edu.swpu.face_detection_register.model.dto.FaceRequestParam;
import cn.edu.swpu.face_detection_register.model.dto.RegisterRequestParam;
import cn.edu.swpu.face_detection_register.model.enums.ExceptionInfoEnum;
import cn.edu.swpu.face_detection_register.model.enums.ImageTypeEnum;
import cn.edu.swpu.face_detection_register.model.vo.DetectFacePartVo;
import cn.edu.swpu.face_detection_register.model.vo.DetectFaceVo;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.service.IFaceDetectionService;
import cn.edu.swpu.face_detection_register.service.IPersonLoginService;
import cn.edu.swpu.face_detection_register.util.JWTUtil;
import cn.edu.swpu.face_detection_register.util.ResponseVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
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
        if (detectFaceVoResponseVo == null || !"0".equals(detectFaceVoResponseVo.getErrorCode()) || detectFaceVoResponseVo.getResult() == null) {
            throw new SystemException(ExceptionInfoEnum.REQUEST_INTERFACE_EXCEPTIO,false);
        }
        return detectFaceVoResponseVo;
    }
    @Override
    public ResponseVo<Boolean> personRegister(RegisterRequestParam registerRequestParam) {
        ResponseVo<DetectFaceVo> detectFaceVoResponseVo = getFaceField(registerRequestParam.getBase64Image(),null);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(UUID.randomUUID().toString().replace("-",""));
        userInfo.setUserName(registerRequestParam.getUserName());
        String faceToken = detectFaceVoResponseVo.getResult().getFaceList().get(0).getFaceToken();
        userInfo.setFaceToken(faceToken);
        //判断是否已经注册，避免重复注册
        UserInfo userInfoSelect = userInfoMapper.selectByFaceToken(faceToken);
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
        //获取到人脸图像对应的face_token
        ResponseVo<DetectFaceVo> detectFaceVoResponseVo = getFaceField(base64Image, null);
        String faceToken = detectFaceVoResponseVo.getResult().getFaceList().get(0).getFaceToken();
        //数据库匹配对应的faceToken，获得对应的UserId
        UserInfo userInfo = userInfoMapper.selectByFaceToken(faceToken);
        if (userInfo == null) {
            throw new SystemException(ExceptionInfoEnum.NOT_REGISTER,null);
        }
        //颁发token
        String token = JWTUtil.createToken(userInfo.getUserId(), secret);
        return ResponseVoUtil.success(token);
    }
}
