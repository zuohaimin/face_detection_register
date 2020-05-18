package cn.edu.swpu.face_detection_register.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GrayScaleFaceSchedule {

    @Scheduled(fixedRate = 5*60*1000)
        public void grayScaleFace(){
      //处理异常信息
    }
}
