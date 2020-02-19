package cn.edu.swpu.face_detection_register.model.vo;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: 季才
 * @Date: 2020/2/19
 * @Description:
 **/
@Getter
@Setter
public class MatchFaceVo {

    @JsonProperty(value = "score")
    private BigDecimal score;

    @JsonProperty(value = "face_list")
    private List<FaceRegisterVo> faceList;
}
