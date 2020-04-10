package cn.edu.swpu.face_detection_register.service;

import cn.edu.swpu.face_detection_register.model.dto.RegisterRequestParam;
import cn.edu.swpu.face_detection_register.model.dto.VerifyUserNameParam;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface IPersonLoginService {

    ResponseVo<Boolean> personRegister(RegisterRequestParam registerRequestParam);

    ResponseVo<Boolean> verifyBase64Image(String base64Image);

    ResponseVo<String> login(String base64Image);

    ResponseVo<Boolean> verifyUserName(VerifyUserNameParam verifyUserNameParam);
}
