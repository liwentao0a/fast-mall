package com.lwt.fastmall.order.config;

import com.lwt.fastmall.common.tool.feign.EnableFeign;
import com.lwt.fastmall.service.tool.jedis.EnableJedis;
import com.lwt.fastmall.service.tool.tk.EnableTkMapper;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

/**
 * @author lwt
 * @date 2019/12/26 20:17
 */
@Configuration
@EnableEurekaClient
@EnableTkMapper
@EnableJedis
@EnableFeign
public class Config {
}
