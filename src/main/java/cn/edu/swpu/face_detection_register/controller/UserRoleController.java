package cn.edu.swpu.face_detection_register.controller;

import cn.edu.swpu.face_detection_register.model.bo.Role;
import cn.edu.swpu.face_detection_register.model.bo.UserInfo;
import cn.edu.swpu.face_detection_register.model.dto.UserRoleParam;
import cn.edu.swpu.face_detection_register.model.dto.VerifyUserNameParam;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.model.vo.UserSelectVo;
import cn.edu.swpu.face_detection_register.service.IUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "用户角色关系")
@RestController
@RequestMapping(value = "/userRole")
@CrossOrigin
public class UserRoleController {

    @Autowired
    private IUserRoleService userRoleService;
    //给用户添加角色
    @ApiOperation(value = "给用户添加角色")
    @PostMapping(value = "/addUserRole")
    public ResponseVo<Boolean> addUserRole(@RequestBody @Valid UserRoleParam userRoleParam){
        return userRoleService.addUserRole(userRoleParam);
    }
    //给用户更新角色
    @ApiOperation(value = "给用户更新角色")
    @PostMapping(value = "/updateUserRole")
    public ResponseVo<Boolean> updateUserRole(@RequestBody @Valid UserRoleParam userRoleParam){
        return userRoleService.updateUserRole(userRoleParam);
    }
    //给用户删除角色
    @ApiOperation(value = "给用户删除角色")
    @PostMapping(value = "/deleteUserRole")
    public ResponseVo<Boolean> deleteUserRole(@RequestBody @Valid UserRoleParam userRoleParam){
        return userRoleService.deleteUserRole(userRoleParam);
    }

    @ApiOperation(value = "查询用户所有角色")
    @PostMapping(value = "/selectUserRole")
    public ResponseVo<List<Role>> selectUserRole(@RequestBody @Valid UserRoleParam userRoleParam){
        return userRoleService.selectUserRole(userRoleParam);
    }

    @ApiOperation(value = "查询用户信息")
    @PostMapping(value = "selectUsers")
    public ResponseVo<List<UserSelectVo>> selectUserList(@RequestBody @Valid VerifyUserNameParam verifyUserNameParam){
        return userRoleService.selectUserList(verifyUserNameParam);
    }

    @ApiOperation(value = "获取当前用户信息")
    @GetMapping(value = "getCurrentUserMsg")
    public ResponseVo<UserSelectVo> getCurrentUserMsg(@RequestHeader(value = "Authorization")String token){
        return userRoleService.getCurrentUserMsg(token);
    }

}
