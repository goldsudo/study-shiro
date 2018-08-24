package com.goldsudo.shiro.realm;

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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {
    Map<String, String> userMap = new HashMap<>(16);

    {
        super.setName("custom");
        userMap.put("Jswang", "ec63f0284164511a75185394766efc0f");
    }

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
        Set<String> set = new HashSet<>();
        set.add("root");
        set.add("admin");
        return set;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        String passWord = userMap.get(userName);
        if (passWord == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName, passWord, "custom");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("goldsudo"));
        return authenticationInfo;
    }

    public static void main(String[] args) {
        //加盐的MD5密码
        Md5Hash md5Hash = new Md5Hash("55555","goldsudo");
        System.out.println(md5Hash.toString());
    }
}
