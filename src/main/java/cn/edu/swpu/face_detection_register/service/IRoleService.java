package cn.edu.swpu.face_detection_register.service;

import cn.edu.swpu.face_detection_register.model.dto.RoleResourceParam;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.model.vo.RoleResourceVo;

import java.util.List;

public interface IRoleService {
    ResponseVo<Integer> deleteRole(List<Long> roleIds);


    ResponseVo<Integer> addRole(RoleResourceParam roleResourceParam);

    ResponseVo<Integer> updateRole(RoleResourceParam roleResourceParam);

    ResponseVo<List<RoleResourceVo>> selectRole(List<Long> roleIds);
}
