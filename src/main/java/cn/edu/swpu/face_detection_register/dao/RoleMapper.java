package cn.edu.swpu.face_detection_register.dao;

import cn.edu.swpu.face_detection_register.model.bo.Role;
import cn.edu.swpu.face_detection_register.model.dto.VerifyRoleNameParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {
    int deleteByPrimaryKey(Long keyId);

    Integer deleteRoleList(List<Long> roleIds);

    int insert(Role record);

    Role selectByPrimaryKey(Long keyId);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);


    List<Role> selectByRoleIds(List<Long> roleIds);

    List<Role> selectRoleByCondition(VerifyRoleNameParam verifyRoleNameParam);

    List<Role> selectRoleByLikeRoleName(VerifyRoleNameParam verifyRoleNameParam);

    Integer updateByRoleName(Role role);

}