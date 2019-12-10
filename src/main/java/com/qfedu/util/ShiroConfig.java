package com.qfedu.util;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public CookieRememberMeManager getCookieRememberMeManager(){
        //创建Cookie
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(30);//30s
        //创建管理Cookie对象
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(simpleCookie);

        return cookieRememberMeManager;
    }


    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

    @Bean
    public realmDemo createShiroRealm(){
        return new realmDemo();
    }

    @Bean
    public SecurityManager cretaeSecurityManager(){

        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(createShiroRealm());
        defaultWebSecurityManager.setRememberMeManager(getCookieRememberMeManager());

        return defaultWebSecurityManager;
    }

    @Bean

    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        //创建Shiro工厂对象
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //如果没有认证将会请求此地址进行认证，请求此地址将由formAuthenticationFilter进行表单认证，跳转到指定资源（比如登陆页面）
        shiroFilterFactoryBean.setLoginUrl("/user/toLogin");
        //没有权限操作时跳转的资源路径
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/refuse");
        //认证成功统一跳转到/index
        //shiroFilterFactoryBean.setSuccessUrl("/index");


        //创建LinkedHashMap对象，需要保证添加顺序（很关键）
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // authc:必须认证通过才可以访问;
        // anon: 匿名访问
        //过滤链定义，从上向下顺序执行，一般将/**放在最下边
        //一般静态资源可以设置匿名访问（不拦截）
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");

        //设置登录URL不拦截
        filterChainDefinitionMap.put("/user/login", "anon"); //直接这样访问相当于什么都不输入直接点一下登录以后的效果

        //设置登录URL不拦截
        filterChainDefinitionMap.put("/", "anon");
        //授权过滤器配置
        filterChainDefinitionMap.put("/user/add","perms[user:add]");//这个url下的资源无法访问 需要在 doGetAuthorizationInfo 中加入permissions.add("user:add");才可以 否则不能访问，会执行shiroFilterFactoryBean.setUnauthorizedUrl("/user/refuse");
        // 退出时，指定logout过滤器
        filterChainDefinitionMap.put("/logout", "logout");

        //必须放在所有权限设置的最后，所有url都必须认证通过才可以访问
        filterChainDefinitionMap.put("/**", "authc");


        //指定url都必须认证通过才可以访问

        //filterChainDefinitionMap.put("/list", "authc");


        //所有url都不需要认证通过就可以访问（匿名访问）

        //filterChainDefinitionMap.put("/**", "anon");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

}
