package cn.edu.swpu.face_detection_register.model.vo;

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
public class FaceRegisterVo {

    @JsonProperty(value = "face_token")
    private String faceToken;

    private Location location;
}
