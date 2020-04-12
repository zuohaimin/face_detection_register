package cn.edu.swpu.face_detection_register.controller;

import cn.edu.swpu.face_detection_register.model.dto.Base64ImageRequestParam;
import cn.edu.swpu.face_detection_register.model.dto.RegisterRequestParam;
import cn.edu.swpu.face_detection_register.model.dto.VerifyUserNameParam;
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
@CrossOrigin
public class PersonLoginController {
    @Autowired
    private IPersonLoginService personLoginService;

    @GetMapping(value = "/")
    public String home(){
        return "index";
    }

    @GetMapping(value = "/background")
    public String background(){
        return "background";
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
    public ResponseVo<String> login(@RequestBody Base64ImageRequestParam base64Image){
        return personLoginService.login(base64Image.getBase64Image());
    }

    @PostMapping(value = "/verifyBase64Image",produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResponseVo<Boolean> verifyBase64Image(@RequestBody @Valid Base64ImageRequestParam base64Image){
        return personLoginService.verifyBase64Image(base64Image.getBase64Image());
    }

    @PostMapping(value = "verifyUserName")
    @ResponseBody
    public ResponseVo<Boolean> verifyUserName(@RequestBody @Valid VerifyUserNameParam verifyUserNameParam) {
        return personLoginService.verifyUserName(verifyUserNameParam);
    }
}
