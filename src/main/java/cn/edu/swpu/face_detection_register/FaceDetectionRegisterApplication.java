package cn.edu.swpu.face_detection_register;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jicai
 */
@SpringBootApplication
@MapperScan("cn.edu.swpu.face_detection_register.dao")
public class FaceDetectionRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaceDetectionRegisterApplication.class, args);
    }

}
