package com.glii.springboot_thymeleaf_shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("order")
public class OrderController {

    @RequestMapping("save")
//    @RequiresRoles("admin")
//    @RequiresRoles(value = {"admin, user"}) //默认同时具有角色
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequiresPermissions("user:update:01")
    public String save() {

        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //代码方式
        if (subject.hasRole("admin")) {
            System.out.println("保存订单！");
        } else {
            System.out.println("无权访问");
        }

        //基于权限字符串

        return "redirect:/index.jsp";
    }
}
