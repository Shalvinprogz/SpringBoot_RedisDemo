package com.redis.test.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.redis.test.domain.UserDO;
import com.redis.test.repository.UserRepository;
import com.redis.test.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@Slf4j
@RestController
public class Controller {

    Gson gson = new Gson();
    private RedisTemplate redisTemplate;
    private UserService userService;

    @Autowired
    public Controller(RedisTemplate redisTemplate, UserService userService) {
        this.redisTemplate = redisTemplate;
        this.userService = userService;
    }

    @PostMapping("user")
    public ResponseEntity<?> addUser(@RequestBody UserDO user) {
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        if(Boolean.TRUE.equals(redisTemplate.hasKey(id))){
            Calendar calendar = Calendar.getInstance();
            log.info("UserDO : " + redisTemplate.opsForValue().get(id));
            UserDO userDO = gson.fromJson((String) redisTemplate.opsForValue().get(id), UserDO.class);
            log.info("The time taken for redis is : " + (Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis()));
            return new ResponseEntity<>(userDO, HttpStatus.OK);
        }else {
            try {
                Calendar calendar = Calendar.getInstance();
                UserDO userDO = userService.getUserById(id);
                log.info("UserDO  : " +  userDO.toString());
                redisTemplate.opsForValue().set(id, gson.toJson(userDO));
                log.info("The time taken for db is : " + (Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis()));
                return new ResponseEntity<>(userDO, HttpStatus.OK);
            }catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
            }
        }
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        if(Boolean.TRUE.equals(redisTemplate.hasKey(id))){
            Calendar calendar = Calendar.getInstance();
            redisTemplate.delete(id);
            log.info("The time taken for deletion from redis is : " + (Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis()));
        }
        try {
            Calendar calendar = Calendar.getInstance();
            userService.deleteUserById(id);
            log.info("Deleted the user Successfully");
            log.info("The time taken for deletion from db is : " + (Calendar.getInstance().getTimeInMillis() -
                    calendar.getTimeInMillis()));
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
