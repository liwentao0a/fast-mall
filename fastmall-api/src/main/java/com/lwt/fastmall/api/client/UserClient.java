package com.lwt.fastmall.api.client;

import com.lwt.fastmall.api.bean.Result;
import com.lwt.fastmall.api.bean.UmsUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@FeignClient(name = "fastmall-user-service")
public interface UserClient {

    /*
    com.lwt.fastmall.user.controller.UserController
     */

    @RequestMapping(value = "/getUserByUsernameAndPassword",method = RequestMethod.GET)
    public Result<UmsUser> getUserByUsernameAndPassword(@RequestParam("username") @NotBlank String username,
                                                        @RequestParam("password") @NotBlank String password);

    @RequestMapping(value = "/getUserById/{userId}",method = RequestMethod.GET)
    public Result<UmsUser> getUserById(@PathVariable("userId") @NotNull Long userId);
}
