package cn.edu.swpu.face_detection_register.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeUtil {

    public static Date LocalDateTime2Date(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant());
    }
}
