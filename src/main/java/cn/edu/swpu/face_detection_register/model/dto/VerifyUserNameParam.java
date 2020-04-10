package cn.edu.swpu.face_detection_register.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class VerifyUserNameParam {
    @NotBlank(message = "用户名不能为空")
    private String userName;
}
