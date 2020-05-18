package cn.edu.swpu.face_detection_register.controller;

import cn.edu.swpu.face_detection_register.model.bo.Role;
import cn.edu.swpu.face_detection_register.model.dto.RoleResourceParam;
import cn.edu.swpu.face_detection_register.model.dto.VerifyRoleNameParam;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.model.vo.RoleResourceVo;
import cn.edu.swpu.face_detection_register.model.vo.TransferGroupDataVo;
import cn.edu.swpu.face_detection_register.model.vo.TransferResourceVo;
import cn.edu.swpu.face_detection_register.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@Api(value = "角色功能")
@RestController
@RequestMapping(value = "/role")
@CrossOrigin
public class RoleController {
    @Autowired
    private IRoleService roleService;
    //删除角色
    @ApiOperation(value = "删除角色")
    @PostMapping(value = "/deleteRole")
    public ResponseVo<Integer> deleteRole(@RequestBody List<String> roleNameList){
        return roleService.deleteRole(roleNameList);
    }
    //添加角色，关联对应资源
    @ApiOperation(value = "添加角色")
    @PostMapping(value = "/addRole")
    public ResponseVo<Integer> addRole(@RequestBody @Valid RoleResourceParam roleResourceParam){
        return roleService.addRole(roleResourceParam);
    }
    //更新角色
    @ApiOperation(value = "更新角色")
    @PostMapping(value = "/updateRole")
    public ResponseVo<Integer> updateRole(@RequestBody @Valid RoleResourceParam roleResourceParam){
        return roleService.updateRole(roleResourceParam);
    }
    //查询角色
    @ApiOperation(value = "查询角色")
    @PostMapping(value = "/selectRole")
    public ResponseVo<List<RoleResourceVo>> selectRole(@RequestBody @Valid List<Long> roleIds){
        return roleService.selectRole(roleIds);
    }

    @ApiOperation(value = "查询所有角色")
    @PostMapping(value = "/selectAllRole")
    public ResponseVo<List<Role>> selectAllRole(){
        return roleService.selectAllRole();
    }

    @ApiOperation(value = "通过角色名查询角色")
    @PostMapping(value = "selectRoleByLikeRoleName")
    public ResponseVo<List<Role>> selectRoleByLikeRoleName(@RequestBody @Valid VerifyRoleNameParam verifyRoleNameParam) {
        return roleService.selectRoleByLikeRoleName(verifyRoleNameParam);
    }

    @ApiOperation(value = "查询所有资源选择组件信息")
    @PostMapping(value = "/selectAllResource2TansferData")
    public ResponseVo<List<TransferResourceVo>> selectAllResource2TansferData(){
        return roleService.selectAllResource2TansferData();
    }

    @ApiOperation(value = "查询所有资源汇总选择组件信息")
    @PostMapping(value = "/selectAllResource2TransferGroupData")
    public ResponseVo<List<TransferGroupDataVo>> selectAllResource2TransferGroupData(){
        return roleService.selectAllResource2TransferGroupData();
    }

    @ApiOperation(value = "查询所有资源")
    @PostMapping(value = "/selectAllResource")
    public ResponseVo<StringBuilder> selectAllResource(){
        return roleService.selectAllResource();
    }

    @ApiOperation(value = "校验角色名")
    @PostMapping(value = "verifyRoleName")
    public ResponseVo<Boolean> verifyRoleName(@RequestBody @Valid VerifyRoleNameParam verifyRoleNameParam){
        return roleService.verifyRoleName(verifyRoleNameParam);
    }

}
