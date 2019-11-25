package com.lwy.bootws.service.impl;

import com.lwy.bootws.bean.Result;
import com.lwy.bootws.bean.User;
import com.lwy.bootws.dao.UserDao;
import com.lwy.bootws.service.UserService;
import com.lwy.bootws.utils.Jwt;
import com.lwy.bootws.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
        Optional<String> pass =  Optional.ofNullable(user).map(u -> u.getPassword());

        // [2] 比较登录密码
        try {
            if (pass.isPresent() && pass.get().equals(Utils.spa512Encode(password))) {
                // 登录成功将用户信息转为 token 存储于 cookie
                genToken(user);
                return result.sussess();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result.fail();
    }

    /**
     * 生成用户 token
     * @param user
     */
    public void genToken(User user) {
        String token = Jwt.create("lwy", "boot-ws").param("username", user.getName()).token();
        // 将 token 放入 cookie 中
        HttpServletResponse  response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(60);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
