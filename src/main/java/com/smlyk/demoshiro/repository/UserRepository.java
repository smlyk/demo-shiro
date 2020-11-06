package com.smlyk.demoshiro.repository;

import com.smlyk.demoshiro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: always
 * @Date: 2020/11/6 11:17 上午
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {

    User findByUserName(String userName);
}
