package com.goldsudo.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class IniRealmTest {
    IniRealm iniRealm = new IniRealm("classpath:user.ini");

    @Test
    public void testAuthentication() {
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("Jswang", "55555");
        subject.login(token);
        System.out.println(subject.isAuthenticated());
        subject.checkRole("root");
        subject.checkPermissions("file:update","file:delete","file:add");
    }
}
