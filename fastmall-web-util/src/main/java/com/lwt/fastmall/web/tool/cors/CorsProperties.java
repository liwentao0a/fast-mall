package com.lwt.fastmall.web.tool.cors;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lwt
 * @date 2019/11/20 15:48
 */
@Component
@ConfigurationProperties("web.cors")
public class CorsProperties {

    private String addMapping="/*";
    private boolean allowCredentials=true;
    private String[] allowedOrigins={"*"};
    private String[] allowedMethods={"GET","POST","PUT","DELETE"};
    private String[] allowedHeaders={"content-type,x-user-token"};
    private String[] exposedHeaders={"x-redirect"};
    private long maxAge=604800000;
    private int order=1;

    public String getAddMapping() {
        return addMapping;
    }

    public void setAddMapping(String addMapping) {
        this.addMapping = addMapping;
    }

    public boolean isAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public String[] getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(String[] allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public String[] getAllowedMethods() {
        return allowedMethods;
    }

    public void setAllowedMethods(String[] allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public String[] getAllowedHeaders() {
        return allowedHeaders;
    }

    public void setAllowedHeaders(String[] allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    public String[] getExposedHeaders() {
        return exposedHeaders;
    }

    public void setExposedHeaders(String[] exposedHeaders) {
        this.exposedHeaders = exposedHeaders;
    }

    public long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(long maxAge) {
        this.maxAge = maxAge;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
