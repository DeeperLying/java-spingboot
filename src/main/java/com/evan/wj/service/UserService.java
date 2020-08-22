package com.evan.wj.service;

import com.evan.wj.dao.UserDAO;
import com.evan.wj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@CacheConfig(cacheNames = "user#60")
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public boolean isExist(String userName) {
        User user = getByName(userName);
        return null!=user;
    }

    public User getByName(String userName) {
        return userDAO.findByUserName(userName);
    }


    public User get(String userName, String password) {
        return userDAO.getByUserNameAndPassword(userName, password);
    }

    public void add(User user) {
        userDAO.save(user);
    }
}