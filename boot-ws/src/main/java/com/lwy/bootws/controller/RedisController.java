package com.lwy.bootws.controller;

import com.alibaba.fastjson.JSONArray;
import com.lwy.bootws.bean.Result;
import com.lwy.bootws.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RedisController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/api/redis")
    @ResponseBody
    public Result insertCaches(@RequestParam String key) {
        User user = new User();
        List<User> userList = new ArrayList<>();
        user.setId(10).setAge(18).setName("张三");
        userList.add(user);
        try {
            redisTemplate.opsForValue().set(key + ":", JSONArray.toJSON(userList));
            return Result.sussess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

    @GetMapping("/api/redis/get")
    @ResponseBody
    public Result getCaches(@RequestParam String key) {
        try {
            Object  str = redisTemplate.opsForValue().get(String.join(key, ":"));
            return Result.sussess(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail();
    }
}
