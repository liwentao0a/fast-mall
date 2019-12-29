package com.lwt.fastmall.web.tool.cors.config;

import com.lwt.fastmall.web.tool.cors.CorsProperties;
import com.lwt.fastmall.web.tool.cors.interceptor.CorsInterceptors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lwt
 * @date 2019/12/17 13:47
 */
@Configuration
@EnableConfigurationProperties(CorsProperties.class)
public class CorsConfig implements WebMvcConfigurer {

    private CorsProperties corsProperties;

    public CorsConfig(CorsProperties corsProperties){
        this.corsProperties=corsProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(corsProperties.getAddMapping())
                .allowCredentials(corsProperties.isAllowCredentials())
                .allowedOrigins(corsProperties.getAllowedOrigins())
                .allowedMethods(corsProperties.getAllowedMethods())
                .allowedHeaders(corsProperties.getAllowedHeaders())
                .exposedHeaders(corsProperties.getExposedHeaders())
                .maxAge(corsProperties.getMaxAge());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        CorsInterceptors corsInterceptors = new CorsInterceptors()
                .setAllowCredentials(corsProperties.isAllowCredentials())
                .setAllowedOrigins(StringUtils.join(corsProperties.getAllowedOrigins(),","))
                .setAllowedMethods(StringUtils.join(corsProperties.getAllowedMethods(),","))
                .setAllowedHeaders(StringUtils.join(corsProperties.getAllowedHeaders(),","))
                .setExposedHeaders(StringUtils.join(corsProperties.getExposedHeaders(),","))
                .setMaxAge(corsProperties.getMaxAge());

        registry.addInterceptor(corsInterceptors)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**")
                .order(corsProperties.getOrder());
    }
}
