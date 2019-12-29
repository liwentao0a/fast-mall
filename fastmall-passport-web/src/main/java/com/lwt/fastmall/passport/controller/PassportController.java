package com.lwt.fastmall.passport.controller;

import com.lwt.fastmall.api.bean.Result;
import com.lwt.fastmall.api.bean.UmsUser;
import com.lwt.fastmall.api.client.UserClient;
import com.lwt.fastmall.api.constant.CodeEnum;
import com.lwt.fastmall.api.constant.Key;
import com.lwt.fastmall.api.constant.RoleEnum;
import com.lwt.fastmall.api.constant.TimeConstant;
import com.lwt.fastmall.common.util.JwtUtils;
import com.lwt.fastmall.common.util.ObjectUtils;
import com.lwt.fastmall.common.util.ResultUtils;
import com.lwt.fastmall.common.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lwt
 * @date 2019/12/16 23:00
 */
@RestController
@Validated
@CrossOrigin
public class PassportController {

    @Autowired
    private UserClient userClient;

    /**
     * token校验
     * @param token
     * @param originIp
     * @return
     */
    @RequestMapping(value = "/verify",method = RequestMethod.GET)
    public Result verify(@RequestParam("token") @NotBlank String token,
                         @RequestParam(value = "originIp",required = false) String originIp){
        Map<String, Object> body = JwtUtils.verifyToken(Key.FASTMALL_KEY, originIp, token);
        if (ObjectUtils.isBlank(body)){
            return ResultUtils.result(CodeEnum.RETURN_FALSE);
        }
        return ResultUtils.success();
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @param request
     * @return
     */
    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    public Result<String> userLogin(@RequestParam("username") @NotBlank String username,
                                @RequestParam("password") @NotBlank String password,
                                @RequestParam(value = "rememberMe",required = false) String rememberMe,
                                HttpServletRequest request, HttpServletResponse response){
        return login(username, password, rememberMe,RoleEnum.USER, request, response);
    }

    private Result<String> login(String username,String password,String rememberMe,RoleEnum roleEnum, HttpServletRequest request, HttpServletResponse response) {
        Result<UmsUser> userResult = userClient.getUserByUsernameAndPassword(username, password);
        if (userResult.getCode() == CodeEnum.SUCCESS.getCode()) {
            UmsUser user = userResult.getData();
            long roleLevel = user.getRoleLevel();
            if (roleLevel < roleEnum.getLevel()) {
                return ResultUtils.result(CodeEnum.PERMISSION_NO_ACCESS);
            }
            String ip = ServletUtils.getRealIp(request);
            Map<String, Object> body = new HashMap<>();
            body.put("userId", user.getId());
            body.put("username", username);
            body.put("nickname", user.getNickname());
            body.put("roleLevel", roleLevel);
            String token = JwtUtils.createToken(Key.FASTMALL_KEY, ip, body);
            if (!ObjectUtils.isBlank(rememberMe) && "on".equals(rememberMe)) {
                Cookie cookieUsername = new Cookie("username", username);
                cookieUsername.setPath("/");
                cookieUsername.setMaxAge(TimeConstant.S_ONE_WEEK);
                response.addCookie(cookieUsername);
            }
            Cookie cookieUserToken = new Cookie("user-token", token);
            cookieUserToken.setPath("/");
            cookieUserToken.setMaxAge(TimeConstant.S_ONE_WEEK);
            response.addCookie(cookieUserToken);
            return ResultUtils.success(token);
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @param request
     * @return
     */
    @RequestMapping(value = "/adminLogin",method = RequestMethod.POST)
    public Result<String> adminLogin(@RequestParam("username") @NotBlank String username,
                                @RequestParam("password") @NotBlank String password,
                                @RequestParam(value = "rememberMe",required = false) String rememberMe,
                                HttpServletRequest request, HttpServletResponse response){
        return login(username, password, rememberMe,RoleEnum.ADMIN, request, response);
    }

}
