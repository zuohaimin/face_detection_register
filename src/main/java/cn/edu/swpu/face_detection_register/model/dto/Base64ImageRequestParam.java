package cn.edu.swpu.face_detection_register.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class Base64ImageRequestParam {
    @NotBlank(message = "Base64图片字符串不能为空")
    private String base64Image;
}
