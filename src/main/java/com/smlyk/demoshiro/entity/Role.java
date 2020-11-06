package com.smlyk.demoshiro.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId; // 编号

    @Column(nullable = false, unique = true)
    private String role; // 角色标识程序中判断使用,如"admin",这个是唯一的:

    private String description; // 角色描述,UI界面显示使用

    private Boolean available = Boolean.TRUE; // 是否可用,如果不可用将不会添加给用户

    //角色 -- 权限关系：多对多关系;
    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name="RolePermission",joinColumns={@JoinColumn(name="roleId")},inverseJoinColumns={@JoinColumn(name="permissionId")})
    private List<Permission> permissions;

    // 用户 - 角色关系定义;
    @ManyToMany
    @JoinTable(name="UserRole",joinColumns={@JoinColumn(name="roleId")},inverseJoinColumns={@JoinColumn(name="userId")})
    private List<User> users;// 一个角色对应多个用户
}