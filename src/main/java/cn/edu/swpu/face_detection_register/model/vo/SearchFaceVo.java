package cn.edu.swpu.face_detection_register.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class SearchFaceVo implements Serializable {

    @JsonProperty(value = "face_token")
    private String faceToken;

    @JsonProperty(value = "user_list")
    private List<UserListVo> userList;
}
