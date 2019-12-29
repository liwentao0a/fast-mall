package com.lwt.fastmall.web.tool.login.interceptor;

import com.alibaba.fastjson.JSON;
import com.lwt.fastmall.api.bean.Result;
import com.lwt.fastmall.api.constant.CodeEnum;
import com.lwt.fastmall.common.util.JwtUtils;
import com.lwt.fastmall.common.util.ObjectUtils;
import com.lwt.fastmall.common.util.ResultUtils;
import com.lwt.fastmall.common.util.ServletUtils;
import com.lwt.fastmall.web.tool.login.LoginAuth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author lwt
 * @date 2019/12/10 14:44
 */
public class LoginAuthInterceptor implements HandlerInterceptor {

    private RestTemplate restTemplate = new RestTemplate();

    private String redirectUrl;

    public LoginAuthInterceptor() {
    }

    public LoginAuthInterceptor(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //静态资源放行
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
        //设置响应头编码
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");
        //登录验证
        //获取LoginAuth注解
        LoginAuth loginAuth = null;
        try {
            HandlerMethod method = (HandlerMethod) handler;
            loginAuth = method.getMethodAnnotation(LoginAuth.class);
            if (loginAuth == null) {
                return true;
            }
        } catch (Exception e) {
            response.getWriter().write(JSON.toJSONString(ResultUtils.result(CodeEnum.PERMISSION_NO_ACCESS)));
            return false;
        }
        //校验token
        int level = loginAuth.role().getLevel();
        boolean loginSuccess = loginAuth.loginSuccess();
        String userToken = getUserToken(request);
        if (!ObjectUtils.isBlank(userToken)) {
            String ip = ServletUtils.getRealIp(request);
            Result result = restTemplate.getForObject("http://localhost:8011/verify?token=" + userToken + "&originIp=" + ip, Result.class);
            if (result.getCode() == CodeEnum.SUCCESS.getCode()) {
                Map<String, Object> body = JwtUtils.getBody(userToken);
                if (body != null) {
                    int roleLevel = (int) body.get("roleLevel");
                    if (roleLevel >= level) {
                        request.setAttribute("userId", body.get("userId"));
                        request.setAttribute("username", body.get("username"));
                        request.setAttribute("nickname", body.get("nickname"));
                        return true;
                    }
                }
            }
        }
        if (!loginSuccess) {
            return true;
        }
        //验证不通过
        //删除user-token的cookie
        Cookie cookie = new Cookie("user-token", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        boolean failRedirect = loginAuth.failRedirect();
        if (failRedirect) {
            String failRedirectUrl = loginAuth.failRedirectUrl();
            StringBuffer requestURL = request.getRequestURL();
            if (ObjectUtils.isBlank(failRedirectUrl)) {
                failRedirectUrl = redirectUrl;
            }
            response.sendRedirect(failRedirectUrl + "?callbackUrl=" + requestURL);
        } else {
            response.getWriter().write(JSON.toJSONString(ResultUtils.result(CodeEnum.PERMISSION_NO_ACCESS)));
        }
        return false;
    }

    /**
     * 从请求头、cookie、请求参数中获取user-token
     *
     * @param request
     * @return
     */
    private String getUserToken(HttpServletRequest request) {
        //请求头中获取
        String userToken = request.getHeader("x-user-token");
        if (StringUtils.isBlank(userToken)) {
            //cookie获取
            userToken = ServletUtils.getCookieVal(request, "user-token");
        }
        if (StringUtils.isBlank(userToken)) {
            //参数获取
            userToken = request.getParameter("user-token");
        }
        return userToken;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
