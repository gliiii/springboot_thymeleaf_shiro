package com.glii.springboot_thymeleaf_shiro.shrio.realm;



import com.glii.springboot_thymeleaf_shiro.entity.Perms;
import com.glii.springboot_thymeleaf_shiro.entity.User;
import com.glii.springboot_thymeleaf_shiro.salt.MyByteSource;
import com.glii.springboot_thymeleaf_shiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

//自定义realm
public class CustomerRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>doGetAuthorizationInfo");
        //获取身份信息
        String primaryPrincipal =(String) principalCollection.getPrimaryPrincipal();
        //根据主身份信息获取角色和权限信息
//        UserService userService = (UserService) ApplicationContextUtil.getBean("userService");
        User user = userService.findRolesByUserName(primaryPrincipal);
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            user.getRoles().forEach(role->{
                simpleAuthorizationInfo.addRole(role.getRole());
                List<Perms> perms = userService.findPermsByRoleId(role.getId());
                if (!CollectionUtils.isEmpty(perms)) {
                    perms.forEach(perm->{
                        simpleAuthorizationInfo.addStringPermission(perm.getPermission());
                    });
                }
            });
            return simpleAuthorizationInfo;
        }

//        if ("ligen".equals(primaryPrincipal)) {
//            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//            simpleAuthorizationInfo.addRole("admin");
//
//            simpleAuthorizationInfo.addStringPermission("user:select:*");
//            simpleAuthorizationInfo.addStringPermission("user:update:02");
//            return simpleAuthorizationInfo;
//        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>doGetAuthenticationInfo");
        //根据身份信息
        String principal =(String) token.getPrincipal();
        //在工厂中获取service对象
//        UserService userService = (UserService) ApplicationContextUtil.getBean("userService");
        User user = userService.findByUserName(principal);
        if (!ObjectUtils.isEmpty(user)) {
            return new SimpleAuthenticationInfo(user.getAccount(), user.getPassword(), new MyByteSource(user.getSalt()), this.getName());
        }
        return null;
    }
}
