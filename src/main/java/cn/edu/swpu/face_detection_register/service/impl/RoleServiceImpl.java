package cn.edu.swpu.face_detection_register.service.impl;

import cn.edu.swpu.face_detection_register.dao.ResourceMapper;
import cn.edu.swpu.face_detection_register.dao.RoleMapper;
import cn.edu.swpu.face_detection_register.dao.RoleResourceMapper;
import cn.edu.swpu.face_detection_register.exception.SystemException;
import cn.edu.swpu.face_detection_register.model.bo.Resource;
import cn.edu.swpu.face_detection_register.model.bo.Role;
import cn.edu.swpu.face_detection_register.model.bo.RoleResource;
import cn.edu.swpu.face_detection_register.model.dto.RoleResourceParam;
import cn.edu.swpu.face_detection_register.model.enums.ExceptionInfoEnum;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.model.vo.RoleResourceVo;
import cn.edu.swpu.face_detection_register.service.IRoleService;
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
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseVo<Integer> deleteRole(List<Long> roleIds) {
        //删除角色表
        List<Long> roleIdsNew = new ArrayList<>(new HashSet<>(roleIds));
        Integer roleDeleteNum = roleMapper.deleteRoleList(roleIdsNew);
        //删除角色 资源映射表
        Integer resourceIds = roleResourceMapper.deleteByRoleId(roleIds);
        if (roleIdsNew.size() == roleDeleteNum && resourceIds >= roleDeleteNum) {
            log.info("角色资源映射删除异常，操作回滚");
            throw new SystemException(ExceptionInfoEnum.ROLE_RESOURCE_MAPPING_DEAL_EXCEPTION,null);
        }
        return ResponseVoUtil.success(roleDeleteNum);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseVo<Integer> addRole(RoleResourceParam roleResourceParam) {
        //添加角色
        Role role = roleResourceParam.getRole();
        Integer roleNum = roleMapper.insert(role);
        //添加角色映射关系
        List<RoleResource> roleResources = getRoleResourceList(roleResourceParam.getResourceId(),role.getKeyId());
        Integer roleResourceNum = roleResourceMapper.insertRoleResourceList(roleResources);
        if (roleNum == 0 || roleResourceNum == 0) {
            log.info("角色资源映射添加异常，操作回滚");
            throw new SystemException(ExceptionInfoEnum.ROLE_RESOURCE_MAPPING_DEAL_EXCEPTION,null);

        }
        return ResponseVoUtil.success(roleNum);
    }

    @Override
    public ResponseVo<Integer> updateRole(RoleResourceParam roleResourceParam) {
        //更新角色信息-动态更新
        Role role = roleResourceParam.getRole();
        Integer roleNum = roleMapper.updateByPrimaryKey(role);
        //更新角色映射信息
        //将之前的角色映射信息删除掉
        //插入这次新增的角色映射信息
        List<Long> roleIdList = new ArrayList<>();
        roleIdList.add(role.getKeyId());
        Integer deleteMappingNum = roleResourceMapper.deleteByRoleId(roleIdList);
        List<RoleResource> roleResourceList = getRoleResourceList(roleResourceParam.getResourceId(), role.getKeyId());
        Integer roleResourceNumber = roleResourceMapper.insertRoleResourceList(roleResourceList);
        if (roleResourceNumber == 0 || deleteMappingNum == 0) {
            log.info("角色资源映射更新异常，操作回滚");
            throw new SystemException(ExceptionInfoEnum.ROLE_RESOURCE_MAPPING_DEAL_EXCEPTION,null);
        }
        return ResponseVoUtil.success(roleNum);
    }

    @Override
    public ResponseVo<List<RoleResourceVo>> selectRole(List<Long> roleIds) {
        //查询角色
        List<Role> roleList = roleMapper.selectByRoleIds(roleIds);
        //查询角色对应的资源信息
        List<RoleResourceVo> roleResourceVoList = new ArrayList<>();

        for (Role role : roleList) {
            //查询映射关系
            List<RoleResource> roleResources = roleResourceMapper.selectByRoleId(role.getKeyId());
            Set<Long> resourceIdSet = new HashSet<>();
            roleResources.forEach(o->{
                resourceIdSet.add(o.getResourceId());
            });
            //查询资源列表
            List<Resource> resourceList = resourceMapper.selectByResourceIds(new ArrayList<>(resourceIdSet));
            RoleResourceVo roleResourceVo = new RoleResourceVo();
            roleResourceVo.setRole(role);
            roleResourceVo.setResourceList(resourceList);
            roleResourceVoList.add(roleResourceVo);
        }
        return ResponseVoUtil.success(roleResourceVoList);
    }

    private List<RoleResource> getRoleResourceList(List<Long> resourceIdList,Long roleId){
        List<RoleResource> roleResources = new ArrayList<>();
        resourceIdList.forEach(o->{
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(o);
            roleResources.add(roleResource);
        });
        return roleResources;
    }
}
