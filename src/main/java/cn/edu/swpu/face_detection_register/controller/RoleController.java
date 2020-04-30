package cn.edu.swpu.face_detection_register.controller;

import cn.edu.swpu.face_detection_register.model.bo.Role;
import cn.edu.swpu.face_detection_register.model.dto.RoleResourceParam;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.model.vo.RoleResourceVo;
import cn.edu.swpu.face_detection_register.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    //删除角色
    @PostMapping(value = "/deleteRole")
    public ResponseVo<Integer> deleteRole(@RequestParam List<Long> roleIds){
        return roleService.deleteRole(roleIds);
    }
    //添加角色，关联对应资源
    @PostMapping(value = "/addRole")
    public ResponseVo<Integer> addRole(@RequestParam RoleResourceParam roleResourceParam){
        return roleService.addRole(roleResourceParam);
    }
    //更新角色
    @PostMapping(value = "/updateRole")
    public ResponseVo<Integer> updateRole(@RequestParam RoleResourceParam roleResourceParam){
        return roleService.updateRole(roleResourceParam);
    }
    //查询角色
    @PostMapping(value = "/selectRole")
    public ResponseVo<List<RoleResourceVo>> selectRole(@RequestParam List<Long> roleIds){
        return roleService.selectRole(roleIds);
    }

}
