package com.lwt.fastmall.user.config;

import com.lwt.fastmall.common.tool.feign.EnableFeign;
import com.lwt.fastmall.web.tool.cors.EnableCors;
import com.lwt.fastmall.web.tool.login.EnableLoginAuth;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

/**
 * @author lwt
 * @date 2019/12/16 22:33
 */
@Configuration
@EnableEurekaClient
@EnableFeign
@EnableCors
@EnableLoginAuth
public class Config {
}
