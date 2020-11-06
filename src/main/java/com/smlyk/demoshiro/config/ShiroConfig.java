package com.smlyk.demoshiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: always
 * @Date: 2020/11/6 11:43 上午
 */
@Configuration
public class ShiroConfig {

    //将自己的验证方式加入容器
    @Bean
    MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    //凭证匹配器（密码校验交给Shiro的SimpleAuthenticationInfo进行处理)
    @Bean
    public MyHashedCredentialsMatcher hashedCredentialsMatcher(){
        MyHashedCredentialsMatcher hashedCredentialsMatcher = new MyHashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean
    DefaultSecurityManager securityManager(){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myShiroRealm());
        return manager;
    }

    // Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        Map<String, String> filterMap = new HashMap<String, String>();
        // 登出
        filterMap.put("/logout", "logout");
        // 对所有用户认证
        filterMap.put("/**", "authc");

        //自己写的登录，不用FormAuthenticationFilter
        filterMap.put("/login", "anon");

        // 登录跳转
        bean.setLoginUrl("/loginFirst");
        // 首页
        bean.setSuccessUrl("/index");
        // 未授权页面，认证不通过跳转
        bean.setUnauthorizedUrl("/403");
        bean.setFilterChainDefinitionMap(filterMap);
        return bean;
    }

    //开启shiro aop注解支持.
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }


}
