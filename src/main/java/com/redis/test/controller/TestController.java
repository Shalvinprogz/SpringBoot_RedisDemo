package com.redis.test.controller;

import com.redis.test.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    private RedisService redisService;

    @Autowired
    public TestController(RedisService redisService) {
        this.redisService = redisService;
    }

    @PostMapping("/put")
    public String put(@RequestParam("key") String key, @RequestBody String value) {
        try {
            redisService.set(key, value);
            return "success";
        } catch (Exception e){
            return "fail";
        }
    }

    @GetMapping
    public Object get(@RequestParam("key") String key) {
        try {
            return redisService.get(key);
        }
        catch (Exception e){
            return "fail";
        }
    }
}
