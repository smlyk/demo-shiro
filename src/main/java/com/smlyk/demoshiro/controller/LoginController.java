package com.smlyk.demoshiro.controller;

import com.smlyk.demoshiro.dto.LoginResult;
import com.smlyk.demoshiro.entity.User;
import com.smlyk.demoshiro.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: always
 * @Date: 2020/11/6 11:53 上午
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping(value = "/loginFirst")
    public String login() {
        return "请先登录";
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody User user) {
        System.out.println("login()");
        String userName = user.getUserName();
        String password = user.getPassword();
        LoginResult loginResult = loginService.login(userName,password);
        if(loginResult.isLogin()){
            return "登录成功";
        } else {
            return "登录失败：" + loginResult.getResult();
        }
    }

    @GetMapping(value = "/index")
    public String index() {
        return "主页";
    }

    @GetMapping(value = "/logout")
    public String logout() {
        return "退出";
    }

    @RequestMapping("/403")
    public String unauthorizedRole(){
        return "没有权限";
    }
}
