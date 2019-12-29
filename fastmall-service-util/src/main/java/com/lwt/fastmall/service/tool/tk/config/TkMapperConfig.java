package com.lwt.fastmall.service.tool.tk.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author lwt
 * @date 2019/12/16 22:19
 */
@Configuration
@MapperScan(basePackages = "com.lwt.fastmall.api.mapper.tk")
public class TkMapperConfig {
}
