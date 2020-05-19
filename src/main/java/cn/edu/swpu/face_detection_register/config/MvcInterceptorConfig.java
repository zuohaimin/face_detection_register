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
        registry.addInterceptor(logControlInterceptor)
                //添加拦截请求路径
                .addPathPatterns("/background/**")
                .addPathPatterns("/role/**")
                .addPathPatterns("/userRole/**")
                //添加不须拦截请求路径
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
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/*")
//                .addResourceLocations("classpath:/static/icon/");
        super.addResourceHandlers(registry);
    }


}
