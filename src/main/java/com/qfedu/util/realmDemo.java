package com.qfedu.util;

import com.qfedu.pojo.Menu;
import com.qfedu.pojo.User;
import com.qfedu.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class realmDemo extends AuthorizingRealm {
    @Autowired
    private IUserService userService;
    private String realmName  = "realmDemo";

    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken
                                                                 authenticationToken) throws AuthenticationException {
//从token中取出用户信息
        //用户名，身份信息
        String principal = (String)authenticationToken.getPrincipal();
        System.out.println(principal);
        //密码，凭证
        Object credentials = authenticationToken.getCredentials();
//类型转化
        String password = new String((char[]) credentials);
        System.out.println(password);
//md5
        Md5Hash md5Hash = new Md5Hash(password, "abcde", 2);
        String password_md5 = md5Hash.toString();
        System.out.println(password_md5);
        //从数据库查出来的密码
        User userLogin = userService.login(principal);
        //String dbpassword = "6585096f3f6735025449cfb351c8cff9";



        if(principal.equals(userLogin.getUserName())&&password_md5.equals(userLogin.getPassword())){

            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal,password,realmName);

            return simpleAuthenticationInfo;
        }

        return null;
    }

    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 从 principals获取主身份信息
        // 将getPrimaryPrincipal方法返回值转为真实身份类型（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型），
        String userName = (String) principalCollection.getPrimaryPrincipal();

        // 根据身份信息获取权限信息
        // 连接数据库...
        // 模拟从数据库获取到数据
        User userLogin = userService.login(userName);
        List<String> permissions = new ArrayList<String>();
        for (Menu menu : userLogin.getMenus()) {
            permissions.add(menu.getPercode());
        }
        //permissions.add("user:list");// 用户的创建
        //permissions.add("user:add");//
        // ....

        // 查到权限数据，返回授权信息(要包括 上边的permissions)
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 将上边查询到授权信息填充到simpleAuthorizationInfo对象中
        simpleAuthorizationInfo.addStringPermissions(permissions);

        return simpleAuthorizationInfo;

    }
}

