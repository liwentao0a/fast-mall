package com.lwt.fastmall.web.tool.login;

import com.lwt.fastmall.api.constant.RoleEnum;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginAuth {

    RoleEnum role() default RoleEnum.USER;

    boolean loginSuccess() default true;

    boolean failRedirect() default false;

    String failRedirectUrl() default "";

}
