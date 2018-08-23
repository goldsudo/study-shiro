package com.goldsudo.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser(){
        //构建一个用户令牌
        simpleAccountRealm.addAccount("Jswang","55555","root","admin");
    }

    @Test
    public void testAuthentication() {
        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);
        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        //3.获取用户令牌并登陆
        UsernamePasswordToken token = new UsernamePasswordToken("Jswang", "55555");
        subject.login(token);
        //4.判断是否登陆成功
        System.out.println(subject.isAuthenticated());
        //5.注销
//        subject.logout();
//        System.out.println(subject.isAuthenticated());
        //6.角色校验
        subject.checkRoles("root","admin");
//        subject.checkRole("asdasdasd");
    }
}
