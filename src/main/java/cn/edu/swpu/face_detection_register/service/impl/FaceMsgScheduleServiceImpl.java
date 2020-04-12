package cn.edu.swpu.face_detection_register.service.impl;

import cn.edu.swpu.face_detection_register.dao.FaceMsgInfoMapper;
import cn.edu.swpu.face_detection_register.dao.UserInfoMapper;
import cn.edu.swpu.face_detection_register.exception.SystemException;
import cn.edu.swpu.face_detection_register.model.bo.FaceMsgInfo;
import cn.edu.swpu.face_detection_register.model.bo.UserInfo;
import cn.edu.swpu.face_detection_register.model.dto.FaceRequestParam;
import cn.edu.swpu.face_detection_register.model.enums.ExceptionInfoEnum;
import cn.edu.swpu.face_detection_register.model.enums.ImageTypeEnum;
import cn.edu.swpu.face_detection_register.model.vo.DetectFacePartVo;
import cn.edu.swpu.face_detection_register.model.vo.DetectFaceVo;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.service.IFaceDetectionService;
import cn.edu.swpu.face_detection_register.service.IFaceMsgScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class FaceMsgScheduleServiceImpl implements IFaceMsgScheduleService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private IFaceDetectionService faceDetectionService;

    @Autowired
    private FaceMsgInfoMapper faceMsgInfoMapper;

    private String faceFeild = "age,beauty,expression,face_shape,gender,glasses,landmark,landmark150,race,quality,eye_status,emotion,face_type,mask";
    @Override
    public void createFaceMsg() {
        //获取数据库isFaceMsg为0的用户数据,分页100
        UserInfo queryParam = new UserInfo();
        queryParam.setPageSize(100);
        queryParam.setIsFaceMsg(0);
        List<UserInfo> userInfos = userInfoMapper.selectUserInfoByCondition(queryParam);
        if (userInfos == null || userInfos.size() == 0){
            log.info("数据库无未获取面部信息的用户,等待下次扫描...");
            return;
        }
        List<FaceMsgInfo> faceMsgInfos = new ArrayList<>();
        FaceRequestParam faceRequestParam = getFaceRequestParam();
        userInfos.forEach(o->{
            faceRequestParam.setImage(o.getFaceToken());
            ResponseVo<DetectFaceVo> detectFaceVoResponseVo = faceDetectionService.detectFace(faceRequestParam);
            FaceMsgInfo faceMsgInfo = getFaceMsgInfo(detectFaceVoResponseVo,o.getUserId());
            faceMsgInfos.add(faceMsgInfo);
        });
        int insertNum = faceMsgInfoMapper.batchInsert(faceMsgInfos);
        if (insertNum != faceMsgInfos.size()) {
            log.info("面部信息插入异常");
            throw new SystemException(ExceptionInfoEnum.FACE_MSG_INSETT_EXCEPTION,null);
        }
        log.info("面部信息插入成功"+insertNum+"条");
        Set<String> userIdSet = new HashSet<>();
        userInfos.forEach(o->{
            userIdSet.add(o.getUserId());
        });
        UserInfo updateParam = new UserInfo();
        updateParam.setUserIdList(new ArrayList<>(userIdSet));
        updateParam.setIsFaceMsg(1);
        Integer updateNum = userInfoMapper.batchUpdateIsFaceMsg(updateParam);
        if (updateNum != userIdSet.size()) {
            log.info("用户信息反向更新异常");
            throw new SystemException(ExceptionInfoEnum.USER_MSG_CALLBACK_EXCEPTION,null);
        }
        log.info("用户信息反向更新成功"+insertNum+"条");
    }

    private FaceRequestParam getFaceRequestParam(){
        FaceRequestParam faceRequestParam = new FaceRequestParam();
        faceRequestParam.setImageType(ImageTypeEnum.FACE_TOKEN.getImageType());
        faceRequestParam.setFaceField(faceFeild);
        return faceRequestParam;
    }

    private FaceMsgInfo getFaceMsgInfo(ResponseVo<DetectFaceVo> detectFaceVoResponseVo,String userId){
        FaceMsgInfo faceMsgInfo = new FaceMsgInfo();
        if (detectFaceVoResponseVo == null || detectFaceVoResponseVo.getResult() == null){
            log.info("获取人脸信息异常");
            throw new SystemException(ExceptionInfoEnum.REQUEST_INTERFACE_EXCEPTIO,null);
        }
        DetectFacePartVo detectFacePartVo = detectFaceVoResponseVo.getResult().getFaceList().get(0);
        faceMsgInfo.setUserId(userId);
        faceMsgInfo.setAge(detectFacePartVo.getAge());
        faceMsgInfo.setBeauty(detectFacePartVo.getBeauty());
        faceMsgInfo.setEmotion(detectFacePartVo.getEmotion().getType());
        faceMsgInfo.setExpression(detectFacePartVo.getExpression().getType());
        faceMsgInfo.setGender(detectFacePartVo.getGender().getType());
        faceMsgInfo.setGlasses(detectFacePartVo.getGlasses().getType());
        faceMsgInfo.setFaceShape(detectFacePartVo.getFaceShape().getType());
        faceMsgInfo.setIsDelete(0);
        return faceMsgInfo;
    }
}
