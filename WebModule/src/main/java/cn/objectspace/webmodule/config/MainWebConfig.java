package cn.objectspace.webmodule.config;

import cn.objectspace.commonmodule.interceptor.LoginCheckInterceptorAdapter;
import cn.objectspace.servicemodule.interceptor.TokenStatusCheckInterceptorAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author Bill
 */
@Configuration
public class MainWebConfig implements WebMvcConfigurer {
    @Resource
    LoginCheckInterceptorAdapter loginCheckInterceptorAdapter;
    @Resource
    TokenStatusCheckInterceptorAdapter tokenStatusCheckInterceptorAdapter;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptorAdapter)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/user/register","/error",
                        "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
                        "/pdf","/pdf/**");
        registry.addInterceptor(tokenStatusCheckInterceptorAdapter)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/user/register","/error",
                        "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
                        "/pdf","/pdf/**");
    }
}
