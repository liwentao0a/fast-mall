package com.lwt.fastmall.page.controller;

import com.lwt.fastmall.api.constant.RoleEnum;
import com.lwt.fastmall.web.tool.login.LoginAuth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lwt
 * @date 2019/12/4 20:54
 */
@Controller
public class PageController {

    @RequestMapping("/favicon.ico")
    public String favicon(){
        return "forward:static/favicon.ico";
    }

    @RequestMapping({"/","/index"})
    public String index(){
        return "index";
    }

    @RequestMapping("/*.do")
    public String go(HttpServletRequest request){
        StringBuffer requestURL = request.getRequestURL();
        String viewName = requestURL.substring(requestURL.lastIndexOf("/"), requestURL.lastIndexOf(".do"));
        return viewName;
    }

    @RequestMapping({"/user/*.do"})
    @LoginAuth(role = RoleEnum.USER,failRedirect = true,failRedirectUrl = "http://localhost:6001")
    public String user(HttpServletRequest request){
        StringBuffer requestURL = request.getRequestURL();
        String viewName = requestURL.substring(requestURL.lastIndexOf("/"), requestURL.lastIndexOf(".do"));
        viewName="role/user/"+viewName;
        return viewName;
    }

    @RequestMapping({"/admin/*.do"})
    @LoginAuth(role = RoleEnum.ADMIN,failRedirect = true,failRedirectUrl = "http://localhost:6001/manage-login.do")
    public String admin(HttpServletRequest request){
        StringBuffer requestURL = request.getRequestURL();
        String viewName = requestURL.substring(requestURL.lastIndexOf("/"), requestURL.lastIndexOf(".do"));
        viewName="role/admin/"+viewName;
        return viewName;
    }
}
