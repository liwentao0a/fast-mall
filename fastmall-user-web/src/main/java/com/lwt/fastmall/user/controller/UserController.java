package com.lwt.fastmall.user.controller;

import com.lwt.fastmall.api.bean.Result;
import com.lwt.fastmall.api.bean.UmsUser;
import com.lwt.fastmall.api.client.UserClient;
import com.lwt.fastmall.common.util.ResultUtils;
import com.lwt.fastmall.web.tool.login.LoginAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * @author lwt
 * @date 2019/12/16 22:38
 */
@RestController
@Validated
@CrossOrigin
public class UserController {

    @Autowired
    private UserClient userClient;

    @LoginAuth
    @RequestMapping(value = "/getUsernameAndNickname",method = RequestMethod.GET)
    public Result<UmsUser> getUsernameAndNickname(HttpServletRequest request){
        String username = (String) request.getAttribute("username");
        String nickname = (String) request.getAttribute("nickname");
        UmsUser umsUser = new UmsUser();
        umsUser.setUsername(username);
        umsUser.setNickname(nickname);
        return ResultUtils.success(umsUser);
    }

    @RequestMapping(value = "/getUserByUsernameAndPassword",method = RequestMethod.GET)
    public Result<UmsUser> getUserByUsernameAndPassword(@RequestParam("username") @NotBlank String username,
                                                        @RequestParam("password") @NotBlank String password){
        return userClient.getUserByUsernameAndPassword(username, password);
    }

}
