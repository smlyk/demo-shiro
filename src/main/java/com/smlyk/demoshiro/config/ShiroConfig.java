package com.smlyk.demoshiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: always
 * @Date: 2020/11/6 11:43 上午
 */
@Configuration
public class ShiroConfig {

    /*
     * a. 告诉shiro不要使用默认的DefaultSubject创建对象，因为不能创建Session
     * */
    @Bean
    public SubjectFactory subjectFactory() {
        return new JwtDefaultSubjectFactory();
    }

    //将自己的验证方式加入容器
    @Bean
    MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCachingEnabled(false);
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
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());

        //Add.2.5
        securityManager.setSessionManager(sessionManager());

        // 关闭 ShiroDAO 功能
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        // 不需要将 Shiro Session 中的东西存到任何地方（包括 Http Session 中）
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        //禁止Subject的getSession方法
        securityManager.setSubjectFactory(subjectFactory());

        return securityManager;
    }

    /**
     * Add.2.4
     * session管理器：
     * sessionManager通过sessionValidationSchedulerEnabled禁用掉会话调度器，
     * 因为我们禁用掉了会话，所以没必要再定期过期会话了。
     * @return
     */
    @Bean
    public DefaultSessionManager sessionManager(){
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }

    // Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());

        /*
         * c. 添加jwt过滤器，并在下面注册
         * 也就是将jwtFilter注册到shiro的Filter中
         * 指定除了login和logout之外的请求都先经过jwtFilter
         * */
        Map<String, Filter> filterMap1 = new HashMap<>();
        //这个地方其实另外两个filter可以不设置，默认就是
//        filterMap1.put("anon", new AnonymousFilter());
        filterMap1.put("authc", new JwtFilter());
//        filterMap1.put("logout", new LogoutFilter());
        bean.setFilters(filterMap1);

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
