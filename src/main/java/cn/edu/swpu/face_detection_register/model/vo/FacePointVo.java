package cn.edu.swpu.face_detection_register.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "表情")
@Getter
@Setter
@ToString
public class FacePointVo {
    private String type;

    private Double probability;
}
