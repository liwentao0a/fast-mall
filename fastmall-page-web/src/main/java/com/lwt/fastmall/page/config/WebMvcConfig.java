package com.lwt.fastmall.page.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lwt
 * @date 2019/12/4 20:49
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                if (request.getDispatcherType()== DispatcherType.FORWARD){
                    return true;
                }
                return false;
            }
        }).addPathPatterns("/static/view/**");

        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String userToken = request.getParameter("user-token");
                if (userToken != null && !userToken.isEmpty()) {
                    Cookie cookie = new Cookie("user-token", userToken);
                    cookie.setPath("/");
                    cookie.setMaxAge(604800);//一周
                    response.addCookie(cookie);
                }
                return true;
            }
        }).addPathPatterns("/**");


    }

}
