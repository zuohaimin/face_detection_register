package cn.edu.swpu.face_detection_register.model.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: 季才
 * @Date: 2020/1/8
 * @Description:
 **/
@Getter
@Setter
@ToString
public class AccessTokenVo {
    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    @JsonProperty(value = "expires_in")
    private String expiresIn;

    @JsonProperty(value = "session_key")
    private String sessionKey;

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "scope")
    private String scope;

    @JsonProperty(value = "session_secret")
    private String sessionSecret;

}
