package cn.edu.swpu.face_detection_register.model.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetectFaceVo {
    /**
     *  {
     *         "face_num": 1,
     *         "face_list": [
     *             {
     *                 "face_token": "3f5012b7828825d37e8baeb927f5ed89",
     *                 "location": {
     *                     "left": 183.05,
     *                     "top": 85.16,
     *                     "width": 56,
     *                     "height": 57,
     *                     "rotation": -37
     *                 },
     *                 "face_probability": 1,
     *                 "angle": {
     *                     "yaw": 6.28,
     *                     "pitch": 2.43,
     *                     "roll": -40.17
     *                 },
     *                 "age": 26,
     *                 "beauty": 60.9,
     *                 "expression": {
     *                     "type": "none",
     *                     "probability": 1
     *                 },
     *                 "face_shape": {
     *                     "type": "oval",
     *                     "probability": 0.36
     *                 },
     *                 "gender": {
     *                     "type": "female",
     *                     "probability": 1
     *                 },
     *                 "race": {
     *                     "type": "yellow",
     *                     "probability": 0.97
     *                 },
     *                 "emotion": {
     *                     "type": "neutral",
     *                     "probability": 0.48
     *                 },
     *                 "face_type": {
     *                     "type": "human",
     *                     "probability": 1
     *                 }
     *             }
     */
    @JsonProperty(value = "face_num")
    private Integer faceNum;

    @JsonProperty(value = "face_list")
    private List<DetectFacePartVo> faceList;
}
