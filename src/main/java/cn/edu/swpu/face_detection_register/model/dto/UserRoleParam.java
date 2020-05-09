package cn.edu.swpu.face_detection_register.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRoleParam {
    private String userId;
    private List<Long> roleIds;
}
