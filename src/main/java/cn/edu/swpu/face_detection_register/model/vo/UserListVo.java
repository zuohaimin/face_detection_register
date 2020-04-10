package cn.edu.swpu.face_detection_register.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserListVo implements Serializable {
    @JsonProperty(value = "group_id")
    private String groupId;

    @JsonProperty(value = "user_id")
    private String userId;

    @JsonProperty(value = "user_info")
    private String userInfo;

    private Float score;
}
