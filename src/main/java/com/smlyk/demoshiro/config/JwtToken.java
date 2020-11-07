package com.smlyk.demoshiro.config;

/**
 * @Author: always
 * @Date: 2020/11/6 4:50 下午
 */
import org.apache.shiro.authc.AuthenticationToken;

//这个就类似UsernamePasswordToken
public class JwtToken implements AuthenticationToken {

    private String jwt;

    private char[] password;

    public JwtToken(String jwt, char[] password) {
        this.jwt = jwt;
        this.password = password;
    }

    @Override//类似是用户名
    public Object getPrincipal() {
        return jwt;
    }

    @Override//类似密码
    public Object getCredentials() {
        return password;
    }
}
