package com.lwy.bootws.service.impl;

import com.lwy.bootws.bean.Result;
import com.lwy.bootws.bean.User;
import com.lwy.bootws.dao.UserDao;
import com.lwy.bootws.service.UserService;
import com.lwy.bootws.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findUsers() {
        return userDao.findUsers();
    }

    @Override
    public Result login(String username, String password) {
        // 1. 根据用户名查询用户
        // 2. 比较登录密码

        Result result = new Result();
        // [1] 根据用户名查询用户
        User user = userDao.findUserByUsername(username);
        String pass =  Optional.ofNullable(user).map(u -> u.getPassword()).orElse(null);

        // [2] 比较登录密码
        try {
            if (!StringUtils.isEmpty(pass) && pass.equals(Utils.spa512Encode(password))) {
                return result.sussess();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result.fail();
    }
}
