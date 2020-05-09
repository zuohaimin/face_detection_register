package cn.edu.swpu.face_detection_register.service;

import cn.edu.swpu.face_detection_register.model.bo.Role;
import cn.edu.swpu.face_detection_register.model.dto.UserRoleParam;
import cn.edu.swpu.face_detection_register.model.dto.VerifyUserNameParam;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.model.vo.UserSelectVo;

import java.util.List;

public interface IUserRoleService {
    ResponseVo<Boolean> addUserRole(UserRoleParam userRoleParam);

    ResponseVo<Boolean> updateUserRole(UserRoleParam userRoleParam);

    ResponseVo<Boolean> deleteUserRole(UserRoleParam userRoleParam);

    ResponseVo<List<Role>> selectUserRole(UserRoleParam userRoleParam);

    ResponseVo<List<UserSelectVo>> selectUserList(VerifyUserNameParam verifyUserNameParam);
}
