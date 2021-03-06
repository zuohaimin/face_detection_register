package cn.edu.swpu.face_detection_register.service;

import java.util.List;

import cn.edu.swpu.face_detection_register.model.dto.FaceRequestParam;
import cn.edu.swpu.face_detection_register.model.vo.*;

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

    /**
     * 人脸匹配，比较两张脸的面部信息
     * @param faceRequestParam
     * @return
     */
    ResponseVo<MatchFaceVo> matchFace(List<FaceRequestParam> faceRequestParam);


    /**
     * 检测人脸,获取人脸参数，后期面部信息的重要接口
     * @param faceRequestParam
     * @return
     */
    ResponseVo<DetectFaceVo> detectFace(FaceRequestParam faceRequestParam);


    ResponseVo<SearchFaceVo> searchFace(FaceRequestParam faceRequestParam);


}
