package com.lwt.fastmall.product.config;

import com.lwt.fastmall.service.tool.jedis.EnableJedis;
import com.lwt.fastmall.service.tool.tk.EnableTkMapper;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

/**
 * @author lwt
 * @date 2019/12/17 15:19
 */
@Configuration
@EnableEurekaClient
@EnableTkMapper
@EnableJedis
public class Config {
}
