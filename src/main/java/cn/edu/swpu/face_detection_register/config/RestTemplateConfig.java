package cn.edu.swpu.face_detection_register.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: 季才
 * @Date: 2020/1/8
 * @Description:
 **/
@Configuration
public class RestTemplateConfig {

    @Bean(value = "restTemplate")
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
