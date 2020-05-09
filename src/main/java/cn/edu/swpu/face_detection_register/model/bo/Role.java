package cn.edu.swpu.face_detection_register.model.bo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class Role {
    private Long keyId;

    @NotBlank(message = "角色名不能为空！")
    private String roleName;

    private Date addTime;

    private Date modifyTime;

    private Integer isDelete;

}