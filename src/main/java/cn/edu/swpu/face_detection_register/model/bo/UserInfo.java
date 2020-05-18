package cn.edu.swpu.face_detection_register.model.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    private String base64Image;

    @Transient
    private Integer isFaceMsg;

    @Transient
    private List<String> userIdList;

    @Transient
    private Integer pageSize;

    @Transient
    private Integer pageIndex;

    public UserInfo() {
    }
}