package com.smlyk.demoshiro.service;

import com.smlyk.demoshiro.dto.LoginResult;

/**
 * @Author: always
 * @Date: 2020/11/6 11:33 上午
 */
public interface LoginService {

    LoginResult login(String userName,String password);

    void logout();

}
