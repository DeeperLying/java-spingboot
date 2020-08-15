package com.evan.wj.controller;

import antlr.collections.impl.LList;
import com.evan.wj.pojo.User;
import com.evan.wj.result.Result;
import com.evan.wj.sendemail.IMailService;
import com.evan.wj.service.UserService;
import org.elasticsearch.common.recycler.Recycler;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    IMailService imailService;

    @RequestMapping(value = "/api/login", method = RequestMethod.GET)
    public Object login(String userName, String password) {
        Map<String, Object> loginInfo = new HashMap<>();
        User user =  userService.get(userName, password);
        if(null == user) {
            return Result.error(500, "用户名密码不正确", null);
        } else {
            loginInfo.put("userInfo", user);
            return Result.success(loginInfo);
        }
    }

    @RequestMapping(value = "/api/reg", method = RequestMethod.POST)
    public Object reg(@RequestBody User user) {
        String userName = user.getUserName();
        Map<String, Object> map = new HashMap();
        User userInfo = userService.getByName(userName);
        if (userInfo == null) {
            // 存储用户账号密码到数据库
            userService.add(user);
            map.put("userInfo", user);
            imailService.sendSimpleMail("2439175930@qq.com", "发送邮件测试", "是否成功");
            return Result.success(map);
        } else {
            return Result.error(500, "用户信息已存在1", null);
        }
    }


    @RequestMapping(value = "/api/info", method = RequestMethod.GET)
    public Object info(String userName, String password) {
        Map<String, Object> loginInfo = new HashMap<>();
        User user =  userService.get(userName, password);
        if(null == user) {
            return Result.error(500, "用户名密码不正确", null);
        } else {
            loginInfo.put("userInfo", user);
            return Result.success(loginInfo);
        }
    }
}
