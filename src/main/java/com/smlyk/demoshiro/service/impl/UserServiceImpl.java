package com.smlyk.demoshiro.service.impl;

import com.smlyk.demoshiro.config.MyHashedCredentialsMatcher;
import com.smlyk.demoshiro.config.ShiroConfig;
import com.smlyk.demoshiro.entity.User;
import com.smlyk.demoshiro.repository.UserRepository;
import com.smlyk.demoshiro.service.UserService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: always
 * @Date: 2020/11/6 11:36 上午
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyHashedCredentialsMatcher hashedCredentialsMatcher;

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public String add(User user) {


        String encodePassword = hashedCredentialsMatcher.hashProvidedCredentials(user.getPassword().toCharArray(),
                ByteSource.Util.bytes(user.getCredentialsSalt()), 2).toHex();
        user.setPassword(encodePassword);

        userRepository.save(user);

        return "userAdd Success";
    }
}
