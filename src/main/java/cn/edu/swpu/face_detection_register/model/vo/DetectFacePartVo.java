package cn.edu.swpu.face_detection_register.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: 季才
 * @Date: 2020/2/20
 * @Description:
 **/
@Getter
@Setter
@ToString
@ApiModel(value = "检测人脸返回参数")
public class DetectFacePartVo implements Serializable {
    @JsonProperty(value = "face_token")
    private String faceToken;

    @JsonProperty(value = "location")
    private Location location;

    @ApiModelProperty(value = "年龄")
    @JsonProperty(value = "age")
    private Double age;

    @ApiModelProperty(value = "美丑得分")
    @JsonProperty(value = "beauty")
    private Double beauty;

    @ApiModelProperty(value = "表情")
    @JsonProperty(value = "expression")
    private FacePointVo expression;

    @ApiModelProperty(value = "性别")
    @JsonProperty(value = "gender")
    private FacePointVo gender;

    @ApiModelProperty(value = "眼镜")
    @JsonProperty(value = "glasses")
    private FacePointVo glasses;

    @ApiModelProperty(value = "脸型")
    @JsonProperty(value = "face_shape")
    private FacePointVo faceShape;

    @JsonProperty(value = "eye_status")
    private Map<String,Double> eyeStatus;

    private FacePointVo emotion;

    @JsonProperty(value = "face_type")
    private FacePointVo faceType;

    private FacePointVo mask;

    private Map<String,Object> quality;





}
