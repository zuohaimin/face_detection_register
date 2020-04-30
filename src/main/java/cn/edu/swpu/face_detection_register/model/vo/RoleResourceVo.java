package cn.edu.swpu.face_detection_register.model.vo;

import cn.edu.swpu.face_detection_register.model.bo.Resource;
import cn.edu.swpu.face_detection_register.model.bo.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
public class RoleResourceVo {
    private Role role;
    private List<Resource> resourceList;
}
