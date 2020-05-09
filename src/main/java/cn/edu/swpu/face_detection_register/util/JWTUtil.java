package cn.edu.swpu.face_detection_register.util;

import cn.edu.swpu.face_detection_register.exception.SystemException;
import cn.edu.swpu.face_detection_register.model.enums.ExceptionInfoEnum;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.spel.ast.NullLiteral;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {



    public static String  createToken(String userId,String secret) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plusMinutes(5);
        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        return JWT.create().withHeader(map)
                    .withClaim("userId",userId)
                    .withIssuedAt(TimeUtil.LocalDateTime2Date(now))
                    .withExpiresAt(TimeUtil.LocalDateTime2Date(expireTime))
                    .sign(Algorithm.HMAC256(secret));
    }

    public static String verifyToken(String token,String secret){
        DecodedJWT decodedJWT = null;
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
            decodedJWT = jwtVerifier.verify(token);
        }catch (Exception e){
            e.printStackTrace();
           throw new SystemException(ExceptionInfoEnum.TOKEN_NOT_VALID,null);
        }
        return decodedJWT.getClaim("userId").toString();
    }

    public static String getUserId(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
           e.printStackTrace();
           throw new SystemException(ExceptionInfoEnum.TOKEN_NOT_VALID, null);
        }
    }
}
