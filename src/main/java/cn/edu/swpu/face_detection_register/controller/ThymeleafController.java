package cn.edu.swpu.face_detection_register.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Api(value = "用户界面")
@Controller
@CrossOrigin
public class ThymeleafController {

    @ApiOperation(value = "用户登陆注册页面")
    @GetMapping(value = {"/","/index"})
    public String index(){
        return "index";
    }

    @ApiOperation(value = "主页面")
    @GetMapping(value = "/home")
    public String home(){
        return "home";
    }
}
