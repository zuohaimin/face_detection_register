package cn.edu.swpu.face_detection_register.model.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Role {
    private Long keyId;

    private String roleName;

    private Date addTime;

    private Date modifyTime;

    private Integer isDelete;

}