package cn.edu.swpu.face_detection_register.service;

import cn.edu.swpu.face_detection_register.model.bo.Role;
import cn.edu.swpu.face_detection_register.model.dto.RoleResourceParam;
import cn.edu.swpu.face_detection_register.model.dto.VerifyRoleNameParam;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.model.vo.RoleResourceVo;
import cn.edu.swpu.face_detection_register.model.vo.TransferGroupDataVo;
import cn.edu.swpu.face_detection_register.model.vo.TransferResourceVo;

import java.util.List;

public interface IRoleService {
    ResponseVo<Integer> deleteRole(List<String> roleNameList);


    ResponseVo<Integer> addRole(RoleResourceParam roleResourceParam);

    ResponseVo<Integer> updateRole(RoleResourceParam roleResourceParam);

    ResponseVo<List<RoleResourceVo>> selectRole(List<Long> roleIds);

    ResponseVo<List<Role>> selectAllRole();

    ResponseVo<StringBuilder> selectAllResource();

    ResponseVo<Boolean> verifyRoleName(VerifyRoleNameParam verifyRoleNameParam);

    ResponseVo<List<Role>> selectRoleByLikeRoleName(VerifyRoleNameParam verifyRoleNameParam);

    ResponseVo<List<TransferResourceVo>> selectAllResource2TansferData();

    ResponseVo<List<TransferGroupDataVo>> selectAllResource2TransferGroupData();
}
