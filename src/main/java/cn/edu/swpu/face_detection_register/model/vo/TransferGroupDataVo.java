package cn.edu.swpu.face_detection_register.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransferGroupDataVo {
    private String groupName;
    private List<TransferResourceVo> groupData;
}
