package cn.edu.swpu.face_detection_register.service;

import cn.edu.swpu.face_detection_register.model.dto.FaceRequestParam;
import cn.edu.swpu.face_detection_register.model.vo.AccessTokenVo;
import cn.edu.swpu.face_detection_register.model.vo.FaceRegisterVo;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;

/**
 * @Author: 季才
 * @Date: 2020/1/8
 * @Description:
 **/
public interface IFaceDetectionService {

    /**
     * 人脸管理库-获取access_token
     * @return AccessTokenVo
     */
    AccessTokenVo getAccessToken();

    /**
     * 人脸管理库-添加人脸
     * @param  faceRequestParam 人脸图像信息
     * @return  ResponseVo<FaceRegisterVo>
     */
    ResponseVo<FaceRegisterVo> registerFace(FaceRequestParam faceRequestParam);


    /**
     * 人脸管理库-更新人脸
     * @param faceRequestParam
     * @return
     */
    ResponseVo<FaceRegisterVo> updateFace(FaceRequestParam faceRequestParam);

    /**
     * 人脸管理库-删除人脸
     * @param faceRequestParam
     * @return
     */
    ResponseVo deleteFace(FaceRequestParam faceRequestParam);


}
