package com.smlyk.demoshiro.service.impl;

import com.smlyk.demoshiro.config.JwtToken;
import com.smlyk.demoshiro.dto.LoginResult;
import com.smlyk.demoshiro.service.LoginService;
import com.smlyk.demoshiro.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: always
 * @Date: 2020/11/6 11:36 上午
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public LoginResult login(String userName, String password) {
        LoginResult loginResult = new LoginResult();
        if (userName == null || userName.isEmpty()) {
            loginResult.setLogin(false);
            loginResult.setResult("用户名为空");
            return loginResult;
        }

        String msg = "";
        // 1、获取Subject实例对象
        Subject currentUser = SecurityUtils.getSubject();

//        // 3、将用户名和密码封装到UsernamePasswordToken
//        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        JwtUtil jwtUtil = new JwtUtil();
        Map<String, Object> chaim = new HashMap<>();
        chaim.put("userName", userName);
        chaim.put("password", password);
        String jwt = jwtUtil.encode(userName, 5 * 60 * 1000, chaim);

        JwtToken jwtToken = new JwtToken(jwt, password.toCharArray());

        // 4、认证
        try {
            currentUser.login(jwtToken);// 传到MyAuthorizingRealm类中的方法进行认证
            loginResult.setLogin(true);
            loginResult.setResult(jwt);
            return loginResult;
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            msg = "UnknownAccountException -- > 账号不存在：";
        } catch (IncorrectCredentialsException e) {
            msg = "IncorrectCredentialsException -- > 密码不正确：";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            msg = "用户验证失败";
        }

        loginResult.setLogin(false);
        loginResult.setResult(msg);

        return loginResult;
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }
}
