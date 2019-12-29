package com.lwt.fastmall.web.tool.login;

import com.lwt.fastmall.web.tool.login.config.LoginAuthConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LoginAuthConfig.class)
public @interface EnableLoginAuth {
}
