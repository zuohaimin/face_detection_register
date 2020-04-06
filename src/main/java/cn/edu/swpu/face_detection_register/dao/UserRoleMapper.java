package cn.edu.swpu.face_detection_register.dao;

import cn.edu.swpu.face_detection_register.model.bo.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper {
    int deleteByPrimaryKey(Long keyId);

    int insert(UserRole record);

    UserRole selectByPrimaryKey(Long keyId);

    List<UserRole> selectAll();

    int updateByPrimaryKey(UserRole record);
}