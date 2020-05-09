package cn.edu.swpu.face_detection_register.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GroupDataVo {
    private String name;
    private Map<Long,String> value;
}
