package com.goldsudo.shiro.realm;

import com.goldsudo.dao.UserDao;
import com.goldsudo.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) principalCollection.getPrimaryPrincipal();
        Set<String> roles = getRolesByUserName(userName);
        Set<String> permissions = getPermissionsByUserName(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> set = new HashSet<>();
        set.add("file:delete");
        set.add("file:update");
        return set;
    }

    private Set<String> getRolesByUserName(String userName) {
        List<String> roleList = userDao.queryRolesByUserName(userName);
        Set<String> set = new HashSet<>(roleList);
        return set;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        String passWord = getPasswordByUserName(userName);
        if (passWord == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName, passWord, "custom");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("goldsudo"));
        return authenticationInfo;
    }

    private String getPasswordByUserName(String userName) {
        User user = userDao.getUserByUserName(userName);
        if (user == null) {
            return null;
        }
        return user.getPassword();
    }

    public static void main(String[] args) {
        //加盐的MD5密码
        Md5Hash md5Hash = new Md5Hash("55555", "goldsudo");
        System.out.println(md5Hash.toString());
    }
}
