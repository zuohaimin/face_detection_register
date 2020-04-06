package cn.edu.swpu.face_detection_register.aop;

import cn.edu.swpu.face_detection_register.exception.SystemException;
import cn.edu.swpu.face_detection_register.model.enums.ExceptionInfoEnum;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvicer {

    @ExceptionHandler(SystemException.class)
    public ResponseVo<Object> handleSystemException(SystemException systemException){
        ResponseVo<Object> responseVo = new ResponseVo<>();
        responseVo.setErrorCode(systemException.getErroCode());
        responseVo.setErrorMsg(systemException.getErroMsg());
        responseVo.setTimestamp(System.currentTimeMillis());
        responseVo.setResult(systemException.getData());
        return responseVo;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseVo<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException validException){
        ResponseVo<Object> responseVo = new ResponseVo<>();
        responseVo.setErrorCode(ExceptionInfoEnum.PARAMETER_VALID_EXCEPTION.getErroCode());
        responseVo.setErrorMsg(validException.getBindingResult().getFieldError().getDefaultMessage());
        responseVo.setTimestamp(System.currentTimeMillis());
        responseVo.setResult(null);
        return responseVo;
    }
}
