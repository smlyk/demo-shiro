package com.smlyk.demoshiro.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;//主键.

    @Column(nullable = false)
    private String permissionName;//名称.

    @Column(columnDefinition="enum('menu','button')")
    private String resourceType;//资源类型，[menu|button]

    private String url;//资源路径.

    private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view

    private Long parentId; //父编号

    private String parentIds; //父编号列表

    private Boolean available = Boolean.TRUE;

    //角色 -- 权限关系：多对多关系;
    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name="RolePermission",joinColumns={@JoinColumn(name="permissionId")},inverseJoinColumns={@JoinColumn(name="roleId")})
    private List<Role> roles;
}