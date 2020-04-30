package cn.edu.swpu.face_detection_register.model.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FaceMsgInfo {
    private Long keyId;

    private String userId;

    private Double age;

    private String faceShape;

    private String gender;

    private String glasses;

    private String emotion;

    private Integer beauty;

    private String expression;

    private Date addTime;

    private Date modifyTime;

    private Integer isDelete;


}