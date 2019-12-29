package com.lwt.fastmall.service.tool.jedis;

import com.lwt.fastmall.service.tool.jedis.config.JedisConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(JedisConfig.class)
public @interface EnableJedis {
}
