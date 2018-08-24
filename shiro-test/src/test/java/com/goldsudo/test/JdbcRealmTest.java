package com.goldsudo.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JdbcRealmTest {
    JdbcRealm jdbcRealm = new JdbcRealm();
    DruidDataSource dataSource = new DruidDataSource();

    {
        //设置数据源
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8&amp");
        dataSource.setUsername("root");
        dataSource.setPassword("jswang");
        jdbcRealm.setDataSource(dataSource);
        //开启权限认证查询，否则将不会进行数据库查询
        jdbcRealm.setPermissionsLookupEnabled(true);
        //自定义认证sql
        jdbcRealm.setAuthenticationQuery("select password from test_user where user_name = ?");
    }

    @Test
    public void testAuthentication() {
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("Jswang", "55555");
        subject.login(token);
        System.out.println(subject.isAuthenticated());
        subject.checkRoles("admin", "root");
        subject.checkPermissions("file:update", "file:delete");
    }
}
