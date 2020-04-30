package cn.edu.swpu.face_detection_register.model.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Resource {
    private Long keyId;

    private String className;

    private String classDesc;

    private String classURL;

    private String methodName;

    private String methodURL;

    private String requestType;

    private String batchNo;

    private Date addTime;

    private Date modifyTime;

    private Integer isDelete;

}