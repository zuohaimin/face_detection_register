package cn.edu.swpu.face_detection_register.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: 季才
 * @Date: 2020/1/8
 * @Description:
 **/
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FaceRequestParam {
    private String image;

    @JsonProperty(value = "image_type")
    private String imageType;

    @JsonProperty(value = "group_id")
    private String groupId;

    @JsonProperty(value = "group_id_list")
    private String groupIdList;

    @JsonProperty(value = "user_id")
    private String userId;

    @JsonProperty(value = "user_info")
    private String userInfo;

    @JsonProperty(value = "quality_control")
    private String qualityControl;

    @JsonProperty(value = "liveness_control")
    private String livenessControl;

    @JsonProperty(value = "action_type")
    private String actionType;

    @JsonProperty(value = "face_token")
    private String faceToken;

    @JsonProperty(value = "face_type")
    private String faceType;

    @JsonProperty(value = "face_field")
    private String faceField;

}
