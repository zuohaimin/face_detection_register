package cn.edu.swpu.face_detection_register.config;

import cn.edu.swpu.face_detection_register.aop.LogControlInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MvcInterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private LogControlInterceptor logControlInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 反选妨碍健康检查
//        registry.addInterceptor(logControlInterceptor).addPathPatterns("/**")
//                .excludePathPatterns("/anno/**")
//                .excludePathPatterns("/swagger-ui.html")
//                .excludePathPatterns("/configuration/ui")
//                .excludePathPatterns("/swagger-resources")
//                .excludePathPatterns("/configuration/security")
//                .excludePathPatterns("/v2/api-docs")
//                .excludePathPatterns("/error")
//                .excludePathPatterns("/webjars/**")
//                .excludePathPatterns("/**/favicon.ico");
        registry.addInterceptor(logControlInterceptor)
                .addPathPatterns("/background/**")
                .addPathPatterns("/role/**")
                .addPathPatterns("/userRole/**")
                .excludePathPatterns("/anno/**")
                .excludePathPatterns("/*")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
        super.addInterceptors(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }


}
