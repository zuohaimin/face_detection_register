package cn.edu.swpu.face_detection_register.model.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Resource {
    private Long keyId;

    private Integer resourceType;

    private String resouceName;

    private String resourceAddress;

    private Date addTime;

    private Date modifyTime;

    private Integer isDelete;

}