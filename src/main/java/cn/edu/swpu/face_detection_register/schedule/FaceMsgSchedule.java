package cn.edu.swpu.face_detection_register.schedule;

import cn.edu.swpu.face_detection_register.service.IFaceMsgScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FaceMsgSchedule {
    @Autowired
    private IFaceMsgScheduleService faceMsgScheduleService;

    @Scheduled(fixedRate = 5*60*1000)
    public void faceMsgSchedule(){
        log.info("开始执行人脸信息扫描任务 ...");
        faceMsgScheduleService.createFaceMsg();
    }

}
