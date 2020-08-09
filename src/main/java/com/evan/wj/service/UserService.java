package com.evan.wj.service;

import com.evan.wj.dao.UserDAO;
import com.evan.wj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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