package cn.edu.swpu.face_detection_register.service.impl;

import cn.edu.swpu.face_detection_register.dao.ResourceMapper;
import cn.edu.swpu.face_detection_register.dao.RoleMapper;
import cn.edu.swpu.face_detection_register.dao.RoleResourceMapper;
import cn.edu.swpu.face_detection_register.exception.SystemException;
import cn.edu.swpu.face_detection_register.model.bo.Resource;
import cn.edu.swpu.face_detection_register.model.bo.Role;
import cn.edu.swpu.face_detection_register.model.bo.RoleResource;
import cn.edu.swpu.face_detection_register.model.dto.RoleResourceParam;
import cn.edu.swpu.face_detection_register.model.dto.VerifyRoleNameParam;
import cn.edu.swpu.face_detection_register.model.dto.VerifyUserNameParam;
import cn.edu.swpu.face_detection_register.model.enums.ExceptionInfoEnum;
import cn.edu.swpu.face_detection_register.model.vo.*;
import cn.edu.swpu.face_detection_register.service.IRoleService;
import cn.edu.swpu.face_detection_register.util.ResponseVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    @Transactional(rollbackFor = SystemException.class)
    public ResponseVo<Integer> deleteRole(List<String> roleNameList) {
        //查询角色keyId
        VerifyRoleNameParam verifyRoleNameParam = new VerifyRoleNameParam();
        verifyRoleNameParam.setRoleName(roleNameList.get(0));
        List<Role> roleList = roleMapper.selectRoleByCondition(verifyRoleNameParam);
        Set<Long> roleIdSet = new HashSet<>();
        roleList.forEach(o->{
            roleIdSet.add(o.getKeyId());
        });
        //删除角色表
        List<Long> roleIdsNew = new ArrayList<>(roleIdSet);
        Integer roleDeleteNum = roleMapper.deleteRoleList(roleIdsNew);
        //删除角色 资源映射表
        roleResourceMapper.deleteByRoleId(roleIdsNew);
        if (roleIdsNew.size() != roleDeleteNum) {
            log.info("角色资源映射删除异常，操作回滚");
            throw new SystemException(ExceptionInfoEnum.ROLE_RESOURCE_MAPPING_DEAL_EXCEPTION,null);
        }
        return ResponseVoUtil.success(roleDeleteNum);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseVo<Integer> addRole(RoleResourceParam roleResourceParam) {
        //添加角色, 对角色名判重
        Role role = roleResourceParam.getRole();
        if (role.getRoleName() == null || "".equals(role.getRoleName().trim())) {
            log.info("【新增角色】 角色名为空");
            throw new SystemException(ExceptionInfoEnum.ROLE_NAME_NULL,null);
        }
        VerifyRoleNameParam verifyRoleNameParam = new VerifyRoleNameParam();
        verifyRoleNameParam.setRoleName(role.getRoleName());
        List<Role> roleList = roleMapper.selectRoleByCondition(verifyRoleNameParam);
        if (roleList != null && roleList.size() > 0) {
            log.info("【新增角色】 角色名重复");
            throw new SystemException(ExceptionInfoEnum.ROLE_NAME_DUPLICATE,null);
        }
        Integer roleNum = roleMapper.insert(role);
        //添加角色映射关系
        List<RoleResource> roleResources = getRoleResourceListByName(roleResourceParam.getMethodNameList(),role.getKeyId());
        if (roleResources != null && roleResources.size() > 0 ) {
            Integer roleResourceNum = roleResourceMapper.insertRoleResourceList(roleResources);
            if (roleNum == 0 || roleResourceNum == 0) {
                log.info("角色资源映射添加异常，操作回滚");
                throw new SystemException(ExceptionInfoEnum.ROLE_RESOURCE_MAPPING_DEAL_EXCEPTION, null);

            }
        }
        return ResponseVoUtil.success(roleNum);
    }

    @Override
    public ResponseVo<Integer> updateRole(RoleResourceParam roleResourceParam) {
        //更新角色信息-动态更新
        //查询角色名对应的roleId
        Role role = roleResourceParam.getRole();
        VerifyRoleNameParam verifyRoleNameParam = new VerifyRoleNameParam();
        verifyRoleNameParam.setRoleName(role.getRoleName());
        List<Role> roleList = roleMapper.selectRoleByCondition(verifyRoleNameParam);
        Long roleId = roleList.get(0).getKeyId();
        Integer roleNum = roleMapper.updateByRoleName(role);
        //更新角色映射信息
        //将之前的角色映射信息删除掉
        //插入这次新增的角色映射信息
        List<Long> roleIdList = new ArrayList<>();
        roleIdList.add(roleId);
        Integer deleteMappingNum = roleResourceMapper.deleteByRoleId(roleIdList);
        List<RoleResource> roleResourceList = getRoleResourceListById(roleResourceParam.getResourceId(), roleId);
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

    private List<RoleResource> getRoleResourceListById(List<Long> resourceIdList,Long roleId){
        List<RoleResource> roleResources = new ArrayList<>();
        resourceIdList.forEach(o->{
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(o);
            roleResources.add(roleResource);
        });
        return roleResources;
    }

    private List<RoleResource> getRoleResourceListByName(List<String> methodNameList,Long roleId){
        //查询methodNameList 对应资源的keyId
        List<Resource> resourceList = resourceMapper.selectResourceByMethodNameList(methodNameList);
        List<RoleResource> roleResources = new ArrayList<>();
        resourceList.forEach(o->{
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(o.getKeyId());
            roleResources.add(roleResource);
        });
        return roleResources;
    }

    @Override
    public ResponseVo<List<Role>> selectAllRole() {
        return ResponseVoUtil.success(roleMapper.selectAll());
    }

    @Override
    public ResponseVo<StringBuilder> selectAllResource() {
        List<Resource> resources = resourceMapper.selectAll();
        //汇总数据，组装多选框需要数据
        List<GroupDataVo> groupDataVos = getGroupDataVo(resources);
        //拼接成前端代码
        //<optgroup label="广东省">
        //<option value="1">广州市</option>
        //<option value="2">深圳市</option>
        //<option value="3">珠海市</option>
        //</optgroup>
        StringBuilder sb = new StringBuilder();
        groupDataVos.forEach(o->{
            sb.append("<optgroup label=\"");
            sb.append(o.getName()).append("\">\n");
            o.getValue().forEach((key,value)->{
                sb.append("<option value=\"").append(key).append("\">");
                sb.append(value).append("</option>\n");
            });
            sb.append("</optgroup>");
        });

        return ResponseVoUtil.success(sb);
    }

    private List<GroupDataVo> getGroupDataVo(List<Resource> resourceList){
        //暴力拆解，有时间可以思考一下怎么优雅的拆解数据
        List<GroupDataVo> groupDataVos = new ArrayList<>();
        Set<String> nameSet = new HashSet<>();
        resourceList.forEach(o->{
            nameSet.add(o.getClassDesc());
        });
       for (String name : nameSet){
           GroupDataVo groupDataVo = new GroupDataVo();
           groupDataVo.setName("".equals(name)? "未知":name);
           Map<Long, String> valueList = new HashMap<>();
           groupDataVo.setValue(valueList);
           groupDataVos.add(groupDataVo);
           resourceList.forEach(o->{
               if (name.equals(o.getClassDesc())) {
                   valueList.put(o.getKeyId(),o.getMethodName());
               }
           });
       }
        return groupDataVos;
    }

    @Override
    public ResponseVo<Boolean> verifyRoleName(VerifyRoleNameParam verifyRoleNameParam) {
        List<Role> roleList = roleMapper.selectRoleByCondition(verifyRoleNameParam);
        if (roleList == null || roleList.size()  < 1){
            return ResponseVoUtil.success(Boolean.TRUE);
        }
        return ResponseVoUtil.success(Boolean.FALSE);
    }

    @Override
    public ResponseVo<List<Role>> selectRoleByLikeRoleName(VerifyRoleNameParam verifyRoleNameParam) {
        List<Role> roleList = roleMapper.selectRoleByLikeRoleName(verifyRoleNameParam);
        if (roleList == null || roleList.size() < 1) {
            log.info("【查询角色】没有相匹配的角色");
            throw new SystemException(ExceptionInfoEnum.ROLE_SELECT_NULL,null);
        }
        return ResponseVoUtil.success(roleList);
    }

    @Override
    public ResponseVo<List<TransferResourceVo>> selectAllResource2TansferData() {
        List<Resource> resources = resourceMapper.selectAll();
        //汇总数据，组装多选框需要数据
        List<TransferResourceVo> transferResourceVos = getTansferData(resources);
        return ResponseVoUtil.success(transferResourceVos);
    }

    private List<TransferResourceVo> getTansferData(List<Resource> resources) {
        List<TransferResourceVo> transferResourceVos = new ArrayList<>();
        resources.forEach(o->{
            TransferResourceVo transferResourceVo = new TransferResourceVo();
            transferResourceVo.setKeyId(o.getKeyId());
            transferResourceVo.setMethodName(o.getMethodName());
            transferResourceVos.add(transferResourceVo);
        });
        return transferResourceVos;
    }

    @Override
    public ResponseVo<List<TransferGroupDataVo>> selectAllResource2TransferGroupData() {
        List<Resource> resources = resourceMapper.selectAll();
        //汇总数据，组装多选框需要数据
        List<TransferGroupDataVo> transferGroupData = getTransferGroupData(resources);
        return ResponseVoUtil.success(transferGroupData);
    }

    private List<TransferGroupDataVo> getTransferGroupData(List<Resource> resources) {
        Set<String> nameSet = new HashSet<>();
        resources.forEach(o->{
            nameSet.add(o.getClassDesc());
        });
        List<TransferGroupDataVo> transferGroupDataVos = new ArrayList<>();
        nameSet.forEach(o->{
            TransferGroupDataVo transferGroupDataVo = new TransferGroupDataVo();
            transferGroupDataVo.setGroupName("".equals(o)? "未知":o);
            List<TransferResourceVo> transferResourceVos = new ArrayList<>();
            transferGroupDataVo.setGroupData(transferResourceVos);
            transferGroupDataVos.add(transferGroupDataVo);
            resources.forEach(resource->{
                if (o.equals(resource.getClassDesc())) {
                    TransferResourceVo transferResourceVo = new TransferResourceVo();
                    transferResourceVo.setKeyId(resource.getKeyId());
                    transferResourceVo.setMethodName(resource.getMethodName());
                    transferResourceVos.add(transferResourceVo);
                }
            });

        });
        return transferGroupDataVos;
    }
}
