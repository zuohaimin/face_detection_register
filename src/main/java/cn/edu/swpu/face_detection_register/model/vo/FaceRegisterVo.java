package cn.edu.swpu.face_detection_register.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: 季才
 * @Date: 2020/1/8
 * @Description:
 **/
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FaceRegisterVo implements Serializable {

    @JsonProperty(value = "face_token")
    private String faceToken;

    private Location location;
}
