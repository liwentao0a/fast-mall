package com.lwt.fastmall.common.tool.feign;

import com.lwt.fastmall.common.tool.feign.config.FeignConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FeignConfig.class)
public @interface EnableFeign {
}
