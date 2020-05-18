package cn.edu.swpu.face_detection_register.schedule;

import cn.edu.swpu.face_detection_register.dao.ResourceMapper;
import cn.edu.swpu.face_detection_register.exception.SystemException;
import cn.edu.swpu.face_detection_register.model.bo.Resource;
import cn.edu.swpu.face_detection_register.model.enums.ExceptionInfoEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UrlResourceSchedule {

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private ResourceMapper resourceMapper;

    @Value(value = "${application.urlResource.isScan:#{true}}")
    private Boolean isScan;

    private List<Map<String, String>> getAllURL() {
        List<Map<String, String>> resultList = new ArrayList<>();

        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean("requestMappingHandlerMapping",RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> mappingInfoHandlerMethodEntry : map.entrySet()) {
            Map<String, String> resultMap = new HashMap<>();

            RequestMappingInfo requestMappingInfo = mappingInfoHandlerMethodEntry.getKey();
            HandlerMethod handlerMethod = mappingInfoHandlerMethodEntry.getValue();

            resultMap.put("className",handlerMethod.getMethod().getDeclaringClass().getName()); // 类名
            Annotation[] parentAnnotations = handlerMethod.getBeanType().getAnnotations();
            for (Annotation annotation : parentAnnotations) {
                if (annotation instanceof Api) {
                    Api api = (Api) annotation;
                    resultMap.put("classDesc",api.value());
                } else if (annotation instanceof RequestMapping) {
                    RequestMapping requestMapping = (RequestMapping) annotation;
                    if (null != requestMapping.value() && requestMapping.value().length > 0) {
                        resultMap.put("classURL",requestMapping.value()[0]);//类URL
                    }
                }
            }
            resultMap.put("methodName", handlerMethod.getMethod().getName()); // 方法名
            Annotation[] annotations = handlerMethod.getMethod().getDeclaredAnnotations();
            if (annotations != null) {
                // 处理具体的方法信息
                for (Annotation annotation : annotations) {
                    if (annotation instanceof ApiOperation) {
                        ApiOperation methodDesc = (ApiOperation) annotation;
                        String desc = methodDesc.value();
                        resultMap.put("methodDesc",desc);//接口描述
                    }
                }
            }
            PatternsRequestCondition p = requestMappingInfo.getPatternsCondition();
            for (String url : p.getPatterns()) {
                resultMap.put("methodURL",url);//请求URL
            }
            RequestMethodsRequestCondition methodsCondition = requestMappingInfo.getMethodsCondition();
            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                resultMap.put("requestType",requestMethod.toString());//请求方式：POST/PUT/GET/DELETE
            }
            resultList.add(resultMap);
        }
        return resultList;
    }

    @PostConstruct
    public void scanUrlResource(){
        if (isScan) {
            return;
        }
        List<Map<String, String>> allURL = getAllURL();
        List<Resource> resourceList = JSON.parseObject(JSON.toJSONString(allURL),new TypeReference<List<Resource>>(){});
        //添加批次号
        String batchNo = System.currentTimeMillis()+"bn";
        List<Resource> resources = resourceList.stream().filter(o->o.getRequestType() != null && o.getMethodURL() != null && o.getClassName() != null && o.getMethodName() != null).collect(Collectors.toList());
        resources.forEach(o->{
            o.setBatchNo(batchNo);
            if (o.getClassDesc() == null) {
                o.setClassDesc("");
            }
            if ( o.getClassURL() == null) {
                o.setClassURL("");
            }
            });
        Integer integer = resourceMapper.batchInsertResource(resources);
        if (integer != resources.size()) {
            isScan = false;
            log.info("扫描全局资源信息异常");
            throw new SystemException(ExceptionInfoEnum.SCAN_URL_RESOURCE_EXCEPTION,null);
        }
        isScan = true;
    }
}
