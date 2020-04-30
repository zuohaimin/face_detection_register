package cn.edu.swpu.face_detection_register.model.enums;


import lombok.Getter;

@Getter
public enum  ExceptionInfoEnum {
    TOKEN_NOT_VALID("601","无效的token"),
    REQUEST_INTERFACE_EXCEPTIO("602","请求接口异常"),
    USERINFO_INSERT_EXCEPTION("603","用户表插入数据异常"),
    FACE_FIELD_ONLY_ONE("604","目前只支持一个人脸信息识别"),
    FACE_FEILD_EYE_CLOSE("605","闭眼"),
    FACE_FEILD_COMPLETENESS("606","人像不完整"),
    FACE_FEILD_WEAR_GLASSES("607","戴墨镜"),
    FACE_FEILD_WEAR_MASK("608","戴口罩"),
    REPEAT_REGISTER("609","重复注册"),
    NOT_REGISTER("610","未注册"),
    PARAMETER_VALID_EXCEPTION("611","参数校验异常"),
    REPEAT_USER_NAME("612","重复的用户名"),
    FACE_MSG_NULL("613","面部信息查询为空"),
    FACE_MSG_INSETT_EXCEPTION("614","面部信息插入异常"),
    USER_MSG_CALLBACK_EXCEPTION("615","用户信息反向更新异常"),
    SCAN_URL_RESOURCE_EXCEPTION("616","扫描全局资源信息异常"),
    ROLE_RESOURCE_MAPPING_DEAL_EXCEPTION("617","角色资源映射处理异常")
    ;
    private String erroCode;
    private String erroMsg;

    ExceptionInfoEnum(String erroCode, String erroMsg) {
        this.erroCode = erroCode;
        this.erroMsg = erroMsg;
    }
}
