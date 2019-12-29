package com.lwt.fastmall.web.tool.login.config;

import com.lwt.fastmall.web.tool.login.interceptor.LoginAuthInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lwt
 * @date 2019/12/10 14:43
 */
@Configuration
public class LoginAuthConfig implements WebMvcConfigurer {

    @Value("${web.loginAuth.redirectUrl:}")
    private String redirectUrl;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginAuthInterceptor loginAuthInterceptor = new LoginAuthInterceptor(redirectUrl);
        registry.addInterceptor(loginAuthInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**")
                .order(2);
    }
}
