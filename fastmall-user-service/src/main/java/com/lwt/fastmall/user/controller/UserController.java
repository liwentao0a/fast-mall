package com.lwt.fastmall.user.controller;

import com.lwt.fastmall.api.bean.Result;
import com.lwt.fastmall.api.bean.UmsUser;
import com.lwt.fastmall.api.constant.CodeEnum;
import com.lwt.fastmall.common.util.ObjectUtils;
import com.lwt.fastmall.common.util.ResultUtils;
import com.lwt.fastmall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author lwt
 * @date 2019/12/16 21:47
 */
@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据用户名和密码获取用户
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/getUserByUsernameAndPassword",method = RequestMethod.GET)
    public Result<UmsUser> getUserByUsernameAndPassword(@RequestParam("username") @NotBlank String username,
                                               @RequestParam("password") @NotBlank String password){
        UmsUser umsUser = userService.getUserByUsernameAndPassword(username, password);
        if (ObjectUtils.isBlank(umsUser)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(umsUser);
    }

    @RequestMapping(value = "/getUserById/{userId}",method = RequestMethod.GET)
    public Result<UmsUser> getUserById(@PathVariable("userId") @NotNull Long userId){
        UmsUser umsUser = userService.getUserById(userId);
        if (ObjectUtils.isBlank(umsUser)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(umsUser);
    }

}
