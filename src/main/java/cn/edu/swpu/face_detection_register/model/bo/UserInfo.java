package cn.edu.swpu.face_detection_register.model.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo implements Serializable {
    private String userId;

    private String userName;

    private String faceToken;

    private Date addTime;

    private Date modifyTime;

    private Integer isLock;

    private Integer isDelete;

    public UserInfo() {
    }
}