package com.jz.dlyk.service.impl;

import com.jz.dlyk.service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 向redis存储一条数据，不带过期时间
     * @param key   存到redis中的键值对的键
     * @param value 存在redis中的键值对的值
     */
    @Override
    public void save(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 删除redis中的一条数据
     * @param key   要删除的键值对的键
     * @return  删除是否成功
     */
    @Override
    public Boolean delete(String key) {
        return stringRedisTemplate.delete(key);
    }

    /**
     * 向redis中存储一条数据，带过期时间
     * @param key   向redis中存储的键值对的键
     * @param value 向redis中存储的键值对的值
     * @param timeout   该键值对的过期时间
     * @param timeUnit  该键值对的过期时间的单位
     */
    @Override
    public void saveWithExpire(String key, String  value, long timeout, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public String get(String key) {
        return (String) stringRedisTemplate.opsForValue().get(key);
    }
}
