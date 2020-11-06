package com.smlyk.demoshiro.service;

import com.smlyk.demoshiro.entity.User;

/**
 * @Author: always
 * @Date: 2020/11/6 11:35 上午
 */
public interface UserService {

    User findByUserName(String userName);

    String add(User user);
}
