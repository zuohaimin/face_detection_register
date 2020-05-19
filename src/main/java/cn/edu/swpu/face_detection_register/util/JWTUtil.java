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
        LocalDateTime expireTime = now.plusMinutes(30);
        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        return JWT.create().withHeader(map)
                    //构造有效载荷
                    .withClaim("userId",userId)
                    .withIssuedAt(TimeUtil.LocalDateTime2Date(now))
                    //构造过期时间
                    .withExpiresAt(TimeUtil.LocalDateTime2Date(expireTime))
                    //构造签名
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
        return decodedJWT.getClaim("userId").asString();
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

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1ODk4NzA2MTYsInVzZXJJZCI6IjMzYTk3MTZhMDQxNjRhNTM5MWYzMmRmYWU3NDRlNDA2IiwiaWF0IjoxNTg5ODY4ODE2fQ.BSPkeTVahdY8zt1D9mdFms2Qy_Ebmj3R0kdL_ki7s2M";
        String s = verifyToken(token, "202045");
        System.out.println(s);
    }
}
