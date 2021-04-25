package com.glii.springboot_thymeleaf_shiro.dao;



import com.glii.springboot_thymeleaf_shiro.entity.Perms;
import com.glii.springboot_thymeleaf_shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDAO {
    //注册用户方法
    void save(User user);
    //根据用户名查询业务的方法
    User findByUserName(String account);
    //根据用户名查询所有角色
    User findRolesByUserName(String account);
    //根据角色id查询权限集合
    List<Perms> findPermsByRoleId(String id);
}
