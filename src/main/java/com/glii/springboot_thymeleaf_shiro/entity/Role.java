package com.glii.springboot_thymeleaf_shiro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    private String id;
    private String role;
    private String desc;

    //定义权限集合
    private List<Perms> perms;
}
