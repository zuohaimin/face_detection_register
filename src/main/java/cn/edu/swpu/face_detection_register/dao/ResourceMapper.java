package cn.edu.swpu.face_detection_register.dao;

import cn.edu.swpu.face_detection_register.model.bo.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceMapper {
    int deleteByPrimaryKey(Long keyId);

    int insert(Resource record);

    Resource selectByPrimaryKey(Long keyId);

    List<Resource> selectAll();

    int updateByPrimaryKey(Resource record);
}