package com.redis.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisService {
    private RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        log.info("set key:{},value:{}", key, value);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
