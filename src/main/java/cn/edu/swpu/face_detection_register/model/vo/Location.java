package cn.edu.swpu.face_detection_register.model.vo;

import java.io.Serializable;
import java.util.function.DoubleUnaryOperator;

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
public class Location implements Serializable {

    private Double left;
    private Double right;
    private Double width;
    private Double height;
    private Integer rotation;

}
