package com.lwy.bootws.controller;

import com.alibaba.fastjson.JSON;
import com.lwy.bootws.bean.Result;
import com.lwy.bootws.bean.User;
import com.lwy.bootws.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(Urls.API_USER)
    @ResponseBody
    public String findUsers() {
        return JSON.toJSONString(userService.findUsers());
    }

    /**
     * 关于 http 请求总结，get 请求传递对象会被自动拆分为单个的字段拼接到url末尾，post
     * 传递对象不会拆分，所以post后端接口需要使用对象组来接值，表单提交需要修改content-type
     * @param user
     * @return
     */
    @PostMapping(Urls.API_USER)
    @ResponseBody
    public Result login(User user) {
        return userService.login(user.getName(), user.getPassword());
    }
}
