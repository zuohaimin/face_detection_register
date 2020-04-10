package cn.edu.swpu.face_detection_register.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ApiModel(value = "表情")
@Getter
@Setter
@ToString
public class FacePointVo implements Serializable {
    private String type;

    private Double probability;
}
