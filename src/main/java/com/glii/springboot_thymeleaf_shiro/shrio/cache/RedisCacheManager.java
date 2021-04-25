package com.glii.springboot_thymeleaf_shiro.shrio.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

//自定义shiro缓存管理器
public class RedisCacheManager implements CacheManager {

    //参数1：认证或者授权缓存的统一名称
    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        System.out.println(cacheName);
        return new RedisCache<K, V>(cacheName);
    }
}
