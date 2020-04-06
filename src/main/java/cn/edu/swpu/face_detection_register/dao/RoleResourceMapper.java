package cn.edu.swpu.face_detection_register.dao;

import cn.edu.swpu.face_detection_register.model.bo.RoleResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleResourceMapper {
    int deleteByPrimaryKey(Long keyId);

    int insert(RoleResource record);

    RoleResource selectByPrimaryKey(Long keyId);

    List<RoleResource> selectAll();

    int updateByPrimaryKey(RoleResource record);
}