package cn.edu.swpu.face_detection_register.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: 季才
 * @Date: 2020/2/20
 * @Description:
 **/
@Getter
@Setter
public class DetectFacePartVo {

    @JsonProperty(value = "face_token")
    private String faceToken;

    private Location location;
}
