package cn.edu.swpu.face_detection_register.aop;

import cn.edu.swpu.face_detection_register.dao.ResourceMapper;
import cn.edu.swpu.face_detection_register.dao.RoleResourceMapper;
import cn.edu.swpu.face_detection_register.dao.UserRoleMapper;
import cn.edu.swpu.face_detection_register.exception.SystemException;
import cn.edu.swpu.face_detection_register.model.bo.Resource;
import cn.edu.swpu.face_detection_register.model.bo.RoleResource;
import cn.edu.swpu.face_detection_register.model.bo.UserRole;
import cn.edu.swpu.face_detection_register.model.dto.ExcutionResultUtil;
import cn.edu.swpu.face_detection_register.model.dto.RegisterRequestParam;
import cn.edu.swpu.face_detection_register.model.enums.ExceptionInfoEnum;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.service.IFaceMsgScheduleService;
import cn.edu.swpu.face_detection_register.service.Pretreat;
import cn.edu.swpu.face_detection_register.util.JWTUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Aspect
@Component
@Slf4j
public class AuthoriseAspectJ {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private Pretreat pretreat;

    @Pointcut("execution(public * cn.edu.swpu.face_detection_register.controller.*.*(..)) && !execution(public * cn.edu.swpu.face_detection_register.controller.PersonLoginController.*(..)) && !execution(public * cn.edu.swpu.face_detection_register.controller.ThymeleafController.*(..))")
    public void pointCut(){}


    @Before(value = "pointCut()")
    public void before(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("【注解：Before】浏览器输入的网址=URL : " + request.getRequestURL().toString() + ", HTTP_METHOD : " + request.getMethod() + ", 执行的业务方法名=CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + ", 业务方法获得的参数=ARGS : " + Arrays.toString(joinPoint.getArgs()));
        //校验相关权限
        String token = request.getHeader("Authorization");
        //获取到Token，获取用户Id
        String userId = JWTUtil.getUserId(token);
        //获取用户Id 对应的资源合集
        Set<Long> userHasResourceIds = getResourceIdListByUserId(userId);


        //查找方法对应资源Id
        String methodName = joinPoint.getSignature().getName();
        List<String> methodNameList = new ArrayList<>();
        methodNameList.add(methodName);
        List<Resource> resourceList = resourceMapper.selectResourceByMethodNameList(methodNameList);
        if (resourceList == null || resourceList.size() != 1) {
            log.info("【获取用户权限】 查询请求方法名对应资源异常！");
            throw new SystemException(ExceptionInfoEnum.SELECT_METHOD_NAME_MAPPING_RESOURCE_EXCEPTION,null);
        }
        //判断资源Id 是否在用户Id对应资源集合里面，是：有对应权限，放行；否：无对应权限，抛出对应异常
        if (userHasResourceIds.contains(resourceList.get(0).getKeyId())){
            log.info("用户userId = {},AOP权限校验通过",userId);
        }else {
            throw new SystemException(ExceptionInfoEnum.NOT_PERMSSION, null);
        }
    }

    private Set<Long> getResourceIdListByUserId(String userId) {
        Set<Long> resourceIdSet = new HashSet<>();
        List<UserRole> userRoles = userRoleMapper.selectByUserId(userId);
        if (userRoles == null || userRoles.size() < 1) {
            log.info("【获取用户权限】 查询用户角色映射表异常！");
            throw new SystemException(ExceptionInfoEnum.USER_HAS_NO_ROLE,null);
        }
        Set<Long> roleIdSet = new HashSet<>();
        userRoles.forEach(o->roleIdSet.add(o.getRoleId()));
        List<RoleResource> roleResourceList = roleResourceMapper.selectByRoleIdList(new ArrayList<>(roleIdSet));
        if (roleResourceList == null || roleResourceList.size() < 1) {
            log.info("【获取用户权限】 查询角色资源映射表异常！");
            throw new SystemException(ExceptionInfoEnum.ROLE_RESOURCE_MAPPING_DEAL_EXCEPTION,null);
        }
        roleResourceList.forEach(o->resourceIdSet.add(o.getResourceId()));
        return resourceIdSet;
    }

    /**
     * 对注册接口切面，对注册人脸进行人脸信息预处理
     */
//    @AfterReturning(value = "execution(public *  cn.edu.swpu.face_detection_register.controller.PersonLoginController.register(*))",returning = "result")
    public void grayScaleFace(JoinPoint joinPoint,ResponseVo<Boolean> result){
       //对注册人脸进行预处理
        //排除注册失败的情况
       if (result == null || !"0".equals(result.getErrorCode())) {
           return;
       }
        //获得请求参数
        Object[] args = joinPoint.getArgs();
        RegisterRequestParam registerRequestParam = (RegisterRequestParam)args[0];
        ExcutionResultUtil excutionResultUtil = pretreat.pretreatImg(registerRequestParam.getBase64Image(), registerRequestParam.getUserName());
        if (!excutionResultUtil.isSuccess()){
            log.error(excutionResultUtil.getMsg());
            throw new SystemException(ExceptionInfoEnum.FACE_PRTREAT_EXCEPTION,null);
        }
        log.info(JSON.toJSONString(excutionResultUtil));
    }
}
