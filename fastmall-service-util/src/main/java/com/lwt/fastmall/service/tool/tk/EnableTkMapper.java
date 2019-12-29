package com.lwt.fastmall.service.tool.tk;

import com.lwt.fastmall.service.tool.tk.config.TkMapperConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TkMapperConfig.class)
public @interface EnableTkMapper {
}
