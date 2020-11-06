package com.smlyk.demoshiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Hash;

/**
 * @Author: always
 * @Date: 2020/11/6 2:25 下午
 */
public class MyHashedCredentialsMatcher extends HashedCredentialsMatcher {
    @Override
    public Hash hashProvidedCredentials(Object credentials, Object salt, int hashIterations) {
        return super.hashProvidedCredentials(credentials, salt, hashIterations);
    }
}
