package com.lwt.fastmall.web.tool.cors.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lwt
 * @date 2019/12/12 18:11
 */
public class CorsInterceptors implements HandlerInterceptor {

    private boolean allowCredentials=true;
    private String allowedMethods="*";
    private String allowedHeaders="";
    private String exposedHeaders="";
    private String allowedOrigins="";
    private long maxAge=604800000;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof ResourceHttpRequestHandler){
            return true;
        }
        String origin = request.getHeader("origin");
        if (!StringUtils.isBlank(origin)) {
            //设置跨域响应头
            if (allowCredentials) {
                response.setHeader("Access-Control-Allow-Credentials", "true");
            }
            if (!StringUtils.isBlank(allowedMethods)) {
                response.setHeader("Access-Control-Allow-Methods", allowedMethods);
            }
            if (!StringUtils.isBlank(allowedHeaders)) {
                response.setHeader("Access-Control-Allow-Headers", allowedHeaders);
            }
            if (!StringUtils.isBlank(exposedHeaders)) {
                response.setHeader("Access-Control-Expose-Headers", exposedHeaders);
            }
            if (StringUtils.isBlank(allowedOrigins)||"*".equals(allowedOrigins)){
                response.setHeader("Access-Control-Allow-Origin",origin);
            }else{
                response.setHeader("Access-Control-Allow-Origin",allowedOrigins);
            }
            response.setHeader("Access-Control-Max-Age",maxAge+"");
            //判断是否是预检请求
            String method = request.getMethod();
            if ("OPTIONS".equals(method)) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("OK");
                return false;
            }
        }
        return true;
    }

    public boolean isAllowCredentials() {
        return allowCredentials;
    }

    public CorsInterceptors setAllowCredentials(boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
        return this;
    }

    public String getAllowedMethods() {
        return allowedMethods;
    }

    public CorsInterceptors setAllowedMethods(String allowedMethods) {
        this.allowedMethods = allowedMethods;
        return this;
    }

    public String getAllowedHeaders() {
        return allowedHeaders;
    }

    public CorsInterceptors setAllowedHeaders(String allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
        return this;
    }

    public String getExposedHeaders() {
        return exposedHeaders;
    }

    public CorsInterceptors setExposedHeaders(String exposedHeaders) {
        this.exposedHeaders = exposedHeaders;
        return this;
    }

    public String getAllowedOrigins() {
        return allowedOrigins;
    }

    public CorsInterceptors setAllowedOrigins(String allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
        return this;
    }

    public long getMaxAge() {
        return maxAge;
    }

    public CorsInterceptors setMaxAge(long maxAge) {
        this.maxAge = maxAge;
        return this;
    }
}
