package com.lwt.fastmall.web.tool.cors;

import com.lwt.fastmall.web.tool.cors.config.CorsConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CorsConfig.class)
public @interface EnableCors {
}
