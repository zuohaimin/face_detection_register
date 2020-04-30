package cn.edu.swpu.face_detection_register.model.dto;

import cn.edu.swpu.face_detection_register.model.bo.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleResourceParam {

    private Role role;
    private List<Long> resourceId;
}
