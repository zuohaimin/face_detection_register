package cn.edu.swpu.face_detection_register.dao;

import cn.edu.swpu.face_detection_register.model.bo.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ResourceMapper {
    int deleteByPrimaryKey(Long keyId);

    int insert(Resource record);

    Resource selectByPrimaryKey(Long keyId);

    List<Resource> selectAll();

    int updateByPrimaryKey(Resource record);

    Integer batchInsertResource(List<Resource> resourceList);

    List<Resource> selectByResourceIds(List<Long> resourceIds);

    List<Resource> selectResourceByMethodNameList(List<String> methodNameList);
}