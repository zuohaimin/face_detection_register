package cn.edu.swpu.face_detection_register.model.vo;

import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: 季才
 * @Date: 2020/1/8
 * @Description:
 **/
@Getter
@Setter
@ToString
public class ResponseVo<T> implements Serializable {
    @JsonProperty(value = "error_code")
    private String errorCode;

    @JsonProperty(value = "error_msg")
    private String errorMsg;

    @JsonProperty(value = "log_id")
    private String logId;

    @JsonProperty(value = "timestamp")
    private long timestamp;

    @JsonProperty(value = "cached")
    private Integer cached;

    @JsonProperty(value = "result")
    private T result;
}
