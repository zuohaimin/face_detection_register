package cn.edu.swpu.face_detection_register.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "注册请求参数")
@Getter
@Setter
public class RegisterRequestParam {

    @NotBlank(message = "上传照片文件流不能为空")
    private String base64Image;

    @NotBlank(message = "用户名不能为空")
    private String userName;
}
