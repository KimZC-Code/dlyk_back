package com.jz.dlyk.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {
    /*
        向redis存储一条数据，不带过期时间
     */
    void save(String key, String value);
    /*
        删除redis中的一条数据
     */
    Boolean delete(String key);
    /*
        向redis中存储一条数据，带过期时间
     */
    void saveWithExpire(String key, String  value, long timeout, TimeUnit timeUnit);
    /*
        通过key从redis中获取value
     */
    String get(String key);
}
