package cn.edu.swpu.face_detection_register.dao;

import cn.edu.swpu.face_detection_register.model.bo.FaceMsgInfo;
import cn.edu.swpu.face_detection_register.model.vo.EchartsDataVo;

import java.util.List;

public interface FaceMsgInfoMapper {
    int deleteByPrimaryKey(Long keyId);

    int insert(FaceMsgInfo record);

    FaceMsgInfo selectByPrimaryKey(Long keyId);

    List<FaceMsgInfo> selectAll();

    int updateByPrimaryKey(FaceMsgInfo record);

    List<EchartsDataVo> selectFaceMsgGroupByAge();

    List<EchartsDataVo> selectFaceMsgGroupByFaceShape();

    List<EchartsDataVo> selectFaceMsgGroupByBeauty();

    List<EchartsDataVo> selectFaceMsgGroupByGender();

    Integer batchInsert(List<FaceMsgInfo> faceMsgInfos);
}