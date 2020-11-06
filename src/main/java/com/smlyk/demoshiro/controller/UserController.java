package com.smlyk.demoshiro.controller;

import com.smlyk.demoshiro.entity.User;
import com.smlyk.demoshiro.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: always
 * @Date: 2020/11/6 11:55 上午
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //用户查询
    @GetMapping("/userList")
    @RequiresPermissions("user:view")//权限管理;
    public String userInfo(){
        return "userList";
    }

    //用户添加
    @PostMapping("/userAdd")
    @RequiresPermissions("user:add")//权限管理;
    public String userInfoAdd(@RequestBody User user){
        return userService.add(user);
    }

    //用户删除
    @GetMapping("/userDel")
    @RequiresPermissions("user:del")//权限管理;
    public String userDel(){
        return "userDel";
    }

}
