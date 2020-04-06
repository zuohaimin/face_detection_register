package cn.edu.swpu.face_detection_register.service;

import cn.edu.swpu.face_detection_register.model.dto.RegisterRequestParam;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;

public interface IPersonLoginService {

    ResponseVo<Boolean> personRegister(RegisterRequestParam registerRequestParam);

    ResponseVo<Boolean> verifyBase64Image(String base64Image);

    ResponseVo<String> login(String base64Image);
}
