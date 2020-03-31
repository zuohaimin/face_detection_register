package cn.edu.swpu.face_detection_register.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("testThymeleaf")
    public String testThymeleaf(){
        return "testThymeleaf";
    }
}
