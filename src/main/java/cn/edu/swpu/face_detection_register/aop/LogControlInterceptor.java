package cn.edu.swpu.face_detection_register.aop;

import cn.edu.swpu.face_detection_register.exception.SystemException;
import cn.edu.swpu.face_detection_register.model.enums.ExceptionInfoEnum;
import cn.edu.swpu.face_detection_register.model.vo.ResponseVo;
import cn.edu.swpu.face_detection_register.util.JWTUtil;
import cn.edu.swpu.face_detection_register.util.ResponseVoUtil;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class LogControlInterceptor implements HandlerInterceptor {

    @Value("${jwt.secret:#{'202045'}}")
    private String secret;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        //获取token
        String token = request.getHeader("Authorization");
        //判空
        if (token == null || "".equals(token.trim())) {
            log.info("【验签】 未获取到token");
            ResponseVo<Object> responseVo = getResponseVo(ExceptionInfoEnum.TOKEN_GET_ERROR);
            addResponseVo(responseVo,response);
            return false;
        }
        //判断有效性
        try {
            JWTUtil.verifyToken(token,secret);
        }catch (SystemException systemException){
            log.info("【验签】"+systemException.getErroMsg());
            ResponseVo<Object> responseVo = getResponseVo(systemException);
            addResponseVo(responseVo,response);
            return false;
        }catch (Exception e){
            ResponseVo<Object> responseVo = getResponseVo(ExceptionInfoEnum.SYSTEM_EXCEPTION);
            addResponseVo(responseVo,response);
            return false;
        }
        return true;
    }
    private void addResponseVo(ResponseVo<Object> responseVo,HttpServletResponse response) throws IOException {
        response.setContentLength(-1);
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.write( new ObjectMapper().writeValueAsString(responseVo).getBytes(StandardCharsets.UTF_8));
        out.flush();
    }
    private ResponseVo<Object> getResponseVo(SystemException systemException) {
        ResponseVo<Object> responseVo = new ResponseVo<>();
        responseVo.setErrorCode(systemException.getErroCode());
        responseVo.setErrorMsg(systemException.getErroMsg());
        responseVo.setTimestamp(System.currentTimeMillis());
        return responseVo;
    }

    private ResponseVo<Object> getResponseVo(ExceptionInfoEnum tokenGetError) {
        ResponseVo<Object> responseVo = new ResponseVo<>();
        responseVo.setErrorCode(ExceptionInfoEnum.TOKEN_GET_ERROR.getErroCode());
        responseVo.setErrorMsg(ExceptionInfoEnum.TOKEN_GET_ERROR.getErroMsg());
        responseVo.setTimestamp(System.currentTimeMillis());
        return responseVo;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }


}
