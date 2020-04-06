package cn.edu.swpu.face_detection_register.exception;

import cn.edu.swpu.face_detection_register.model.enums.ExceptionInfoEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class SystemException extends RuntimeException {

    private String erroCode;
    private String erroMsg;
    private Object data;

    public SystemException(String erroCode, String erroMsg, Object data) {
        this.erroCode = erroCode;
        this.erroMsg = erroMsg;
        this.data = data;
    }

    public SystemException(ExceptionInfoEnum exceptionInfoEnum,Object data) {
        this.erroCode = exceptionInfoEnum.getErroCode();
        this.erroMsg = exceptionInfoEnum.getErroMsg();
        this.data = data;
    }
}
