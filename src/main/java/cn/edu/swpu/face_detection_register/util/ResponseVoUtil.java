package cn.edu.swpu.face_detection_register.util;

import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;


public class ResponseVoUtil<T> {

    private static <T> ResponseVo<T> init(String errorCode,String errorMsg,T result){
        ResponseVo<T> responseVo = new ResponseVo<>();
        responseVo.setErrorCode(errorCode);
        responseVo.setErrorMsg(errorMsg);
        responseVo.setResult(result);
        responseVo.setTimestamp(System.currentTimeMillis());
        return responseVo;
    }

    public static <T> ResponseVo<T> success(T result){
        return init("0","SUCCESS",result);
    }

    public static <T> ResponseVo<T> error(T result){
        return init("1","ERROR", result);
    }
}
