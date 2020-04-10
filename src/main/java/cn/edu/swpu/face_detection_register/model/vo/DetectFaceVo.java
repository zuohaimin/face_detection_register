package cn.edu.swpu.face_detection_register.model.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: 季才
 * @Date: 2020/2/20
 * @Description:
 **/
@Getter
@Setter
@ToString
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetectFaceVo implements Serializable {

    @JsonProperty(value = "face_num")
    private Integer faceNum;

    @JsonProperty(value = "face_list")
    private List<DetectFacePartVo> faceList;
}
