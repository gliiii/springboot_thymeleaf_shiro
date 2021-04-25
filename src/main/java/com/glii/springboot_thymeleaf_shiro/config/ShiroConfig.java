package com.glii.springboot_thymeleaf_shiro.config;



import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.glii.springboot_thymeleaf_shiro.shrio.cache.RedisCacheManager;
import com.glii.springboot_thymeleaf_shiro.shrio.realm.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来整合shiro相关配置类
 */
@Configuration
public class ShiroConfig {

    /**
     * 方言处理，thymeleaf开启
     * @return
     */
    @Bean(name="shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }


    //1.创建shiroFilter
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //配置系统的受限资源
        //配置系统的公共资源
        Map<String, String> map = new HashMap<>();
        map.put("/login.html", "anon");//anon设置为公共资源
        map.put("/user/getImage", "anon");//anon设置为公共资源
        map.put("/user/register", "anon");//anon设置为公共资源
        map.put("/user/registerview", "anon");//anon设置为公共资源
        map.put("/user/login", "anon");//anon设置为公共资源

        map.put("/**", "authc");//authc请求这个资源需要认证和授权

        //系统默认认证界面路径login.jsp,可以自定义
        shiroFilterFactoryBean.setLoginUrl("/user/loginview");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return  shiroFilterFactoryBean;
    }

    //2.创建安全管理器
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器设置realm
        defaultWebSecurityManager.setRealm(realm);

        return defaultWebSecurityManager;
    }

    //3.创建自定义realm
    @Bean
    public Realm getRealm(){
        CustomerRealm customerRealm = new CustomerRealm();
        //修改凭证校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法为md5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(credentialsMatcher);

        //开启缓存管理,指定ehcache
        customerRealm.setCacheManager(new RedisCacheManager());
        //设置全局开启缓存管理
        customerRealm.setCachingEnabled(true);
        //开启认证缓存
        customerRealm.setAuthenticationCachingEnabled(true);
        //设置认证缓存名称，不设置则使用默认名称
        customerRealm.setAuthenticationCacheName("authenticationCache");
        //开启授权缓存
        customerRealm.setAuthorizationCachingEnabled(true);
        //设置授权缓存名称，不设置则使用默认名称
        customerRealm.setAuthorizationCacheName("authorizationCache");

        return customerRealm;
    }

}
