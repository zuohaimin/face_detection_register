package cn.edu.swpu.face_detection_register.controller;

import cn.edu.swpu.face_detection_register.model.bo.Role;
import cn.edu.swpu.face_detection_register.model.dto.RoleResourceParam;
import cn.edu.swpu.face_detection_register.model.dto.VerifyRoleNameParam;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.model.vo.RoleResourceVo;
import cn.edu.swpu.face_detection_register.model.vo.TransferGroupDataVo;
import cn.edu.swpu.face_detection_register.model.vo.TransferResourceVo;
import cn.edu.swpu.face_detection_register.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping(value = "/role")
@CrossOrigin
public class RoleController {
    @Autowired
    private IRoleService roleService;
    //删除角色
    @PostMapping(value = "/deleteRole")
    public ResponseVo<Integer> deleteRole(@RequestBody List<String> roleNameList){
        return roleService.deleteRole(roleNameList);
    }
    //添加角色，关联对应资源
    @PostMapping(value = "/addRole")
    public ResponseVo<Integer> addRole(@RequestBody @Valid RoleResourceParam roleResourceParam){
        return roleService.addRole(roleResourceParam);
    }
    //更新角色
    @PostMapping(value = "/updateRole")
    public ResponseVo<Integer> updateRole(@RequestBody @Valid RoleResourceParam roleResourceParam){
        return roleService.updateRole(roleResourceParam);
    }
    //查询角色
    @PostMapping(value = "/selectRole")
    public ResponseVo<List<RoleResourceVo>> selectRole(@RequestBody @Valid List<Long> roleIds){
        return roleService.selectRole(roleIds);
    }

    @PostMapping(value = "/selectAllRole")
    public ResponseVo<List<Role>> selectAllRole(){
        return roleService.selectAllRole();
    }

    @PostMapping(value = "selectRoleByLikeRoleName")
    public ResponseVo<List<Role>> selectRoleByLikeRoleName(@RequestBody @Valid VerifyRoleNameParam verifyRoleNameParam) {
        return roleService.selectRoleByLikeRoleName(verifyRoleNameParam);
    }

    @PostMapping(value = "/selectAllResource2TansferData")
    public ResponseVo<List<TransferResourceVo>> selectAllResource2TansferData(){
        return roleService.selectAllResource2TansferData();
    }

    @PostMapping(value = "/selectAllResource2TransferGroupData")
    public ResponseVo<List<TransferGroupDataVo>> selectAllResource2TransferGroupData(){
        return roleService.selectAllResource2TransferGroupData();
    }

    @PostMapping(value = "/selectAllResource")
    public ResponseVo<StringBuilder> selectAllResource(){
        return roleService.selectAllResource();
    }

    @PostMapping(value = "verifyRoleName")
    public ResponseVo<Boolean> verifyRoleName(@RequestBody @Valid VerifyRoleNameParam verifyRoleNameParam){
        return roleService.verifyRoleName(verifyRoleNameParam);
    }

}
