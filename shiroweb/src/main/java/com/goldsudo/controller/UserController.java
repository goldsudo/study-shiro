package com.goldsudo.controller;

import com.goldsudo.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UserController {

    @RequestMapping(value = "/login.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String login(User user) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            return e.getMessage();
        }
        if (subject.hasRole("root")) {
            return "登陆成功，具有root权限";
        }
        return "登陆成功，无root权限";
    }

    @RequiresRoles(value={"root","admin"},logical = Logical.OR)
    @RequestMapping(value = "/rootRole.do", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String rootRole() {
        return "这是具有root或admin权限才能访问的api";
    }

    @RequiresRoles("user")
    @RequestMapping(value = "/userRole.do", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String userRole() {
        return "这是具有user权限才能访问的api";
    }
}
