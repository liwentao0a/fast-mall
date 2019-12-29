package com.lwt.fastmall.common.tool.feign.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author lwt
 * @date 2019/12/16 22:35
 */
@Configuration
@EnableFeignClients(basePackages = "com.lwt.fastmall.api.client")
@ComponentScan(basePackages = "com.lwt.fastmall.api.client")
public class FeignConfig {
}
