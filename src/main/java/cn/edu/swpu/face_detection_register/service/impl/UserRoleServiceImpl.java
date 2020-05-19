package cn.edu.swpu.face_detection_register.service.impl;

import cn.edu.swpu.face_detection_register.dao.RoleMapper;
import cn.edu.swpu.face_detection_register.dao.UserInfoMapper;
import cn.edu.swpu.face_detection_register.dao.UserRoleMapper;
import cn.edu.swpu.face_detection_register.exception.SystemException;
import cn.edu.swpu.face_detection_register.model.bo.Role;
import cn.edu.swpu.face_detection_register.model.bo.UserRole;
import cn.edu.swpu.face_detection_register.model.dto.UserRoleParam;
import cn.edu.swpu.face_detection_register.model.dto.VerifyUserNameParam;
import cn.edu.swpu.face_detection_register.model.enums.ExceptionInfoEnum;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.model.vo.UserSelectVo;
import cn.edu.swpu.face_detection_register.service.IUserRoleService;
import cn.edu.swpu.face_detection_register.util.JWTUtil;
import cn.edu.swpu.face_detection_register.util.ResponseVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserRoleServiceImpl implements IUserRoleService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseVo<Boolean> addUserRole(UserRoleParam userRoleParam) {
        //插入角色与用户的映射关系
        List<UserRole> userRoles = getUserRoleList(userRoleParam.getRoleIds(),userRoleParam.getUserId());
        Integer userRoleNum = userRoleMapper.insertUserRoleList(userRoles);
        if (userRoleNum != userRoles.size()){
            log.info("用户角色映射关系操作异常，操作回滚！");
            throw new SystemException(ExceptionInfoEnum.USERINFO_ROLE_MAPPING_DEAL_EXCEPTION,false);
        }
        return ResponseVoUtil.success(true);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseVo<Boolean> updateUserRole(UserRoleParam userRoleParam) {
        //先删除原有的映射关系
        UserRoleParam deleteParam = new UserRoleParam();
        deleteParam.setUserId(userRoleParam.getUserId());
        Integer deleteNum = userRoleMapper.deleteByCondition(deleteParam);
        //插入更新的现有映射关系
        List<UserRole> userRoles = getUserRoleList(userRoleParam.getRoleIds(),userRoleParam.getUserId());
        Integer userRoleNum = userRoleMapper.insertUserRoleList(userRoles);
        if (userRoleNum != userRoles.size() || deleteNum == 0){
            log.info("用户角色映射关系更新异常，操作回滚！");
            throw new SystemException(ExceptionInfoEnum.USERINFO_ROLE_MAPPING_DEAL_EXCEPTION,false);
        }
        return ResponseVoUtil.success(true);
    }

    @Override
    public ResponseVo<Boolean> deleteUserRole(UserRoleParam userRoleParam) {
        Integer deleteMappingNum = userRoleMapper.deleteByCondition(userRoleParam);
        if (deleteMappingNum != userRoleParam.getRoleIds().size()){
            log.info("用户角色映射关系删除异常，操作回滚！");
            throw new SystemException(ExceptionInfoEnum.USERINFO_ROLE_MAPPING_DEAL_EXCEPTION,false);
        }
        return ResponseVoUtil.success(true);
    }

    @Override
    public ResponseVo<List<Role>> selectUserRole(UserRoleParam userRoleParam) {
        List<UserRole> userRoleList = userRoleMapper.selectByUserId(userRoleParam.getUserId());
        Set<Long> roleIdSet = new HashSet<>();
        userRoleList.forEach(o->{
            roleIdSet.add(o.getRoleId());
        });
        if (roleIdSet.size() <1){
            log.info("用户为未关联角色");
            throw new SystemException(ExceptionInfoEnum.USER_HAS_NO_ROLE,null);
        }
        List<Role> roleList = roleMapper.selectByRoleIds(new ArrayList<>(roleIdSet));
        return ResponseVoUtil.success(roleList);
    }

    private List<UserRole> getUserRoleList(List<Long> roleIdList,String userId) {
        List<UserRole> userRoles = new ArrayList<>();
        roleIdList.forEach(o->{
            UserRole userRole = new UserRole();
            userRole.setRoleId(o);
            userRole.setUserId(userId);
            userRoles.add(userRole);
        });
        return userRoles;
    }

    @Override
    public ResponseVo<List<UserSelectVo>> selectUserList(VerifyUserNameParam verifyUserNameParam) {
        List<UserSelectVo> userSelectVos = userInfoMapper.selectUserList(verifyUserNameParam);
        if (userSelectVos == null || userSelectVos.size()<1) {
            throw new SystemException(ExceptionInfoEnum.USER_SELECT_NULL,null);
        }
        return ResponseVoUtil.success(userSelectVos);
    }

    @Override
    public ResponseVo<UserSelectVo> getCurrentUserMsg(String token) {
        String userId = JWTUtil.getUserId(token);
        UserSelectVo userSelectVo = userInfoMapper.selectByUserId(userId);
        if (userSelectVo == null) {
            log.info("获取当前用户信息异常");
            throw new SystemException(ExceptionInfoEnum.USER_SELECT_NULL,null);
        }
        StringBuilder sb = new StringBuilder("data:image/jpg;base64,");
        sb.append(userSelectVo.getBase64Image());
        userSelectVo.setBase64Image(sb.toString());
        return ResponseVoUtil.success(userSelectVo);
    }
}
