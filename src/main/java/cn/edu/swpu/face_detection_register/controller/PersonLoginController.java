package cn.edu.swpu.face_detection_register.controller;

import cn.edu.swpu.face_detection_register.model.dto.RegisterRequestParam;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.service.IPersonLoginService;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/anno")
@ApiModel(value = "用户注册")
public class PersonLoginController {
    @Autowired
    private IPersonLoginService personLoginService;

    @GetMapping(value = "/home")
    public String home(){
        return "home";
    }

    /**
     * 用户注册
     * @param registerRequestParam
     */
    @PostMapping(value = "/register")
    @ResponseBody
    public ResponseVo<Boolean> register(@RequestBody @Valid RegisterRequestParam registerRequestParam){
        return personLoginService.personRegister(registerRequestParam);
    }

    /**
     * 用户登陆
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public ResponseVo<String> login(@RequestBody String base64Image){
        return personLoginService.login(base64Image);
    }

    @PostMapping(value = "/verifyBase64Image")
    @ResponseBody
    public ResponseVo<Boolean> verifyBase64Image(@RequestBody String base64Image){
        return personLoginService.verifyBase64Image(base64Image);
    }

}
