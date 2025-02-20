package com.redis.test.service;

import com.redis.test.domain.UserDO;
import com.redis.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDO getUserById(String id) {
        return userRepository.find(id);
    }

    public void saveUser(UserDO userDO) {
        userRepository.save(userDO);
    }

    public void deleteUserById(String id) {
        UserDO userDO = userRepository.find(id);
        userRepository.delete(userDO);
    }
}
