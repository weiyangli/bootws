package com.lwy.bootws.service;

import com.lwy.bootws.bean.Result;
import com.lwy.bootws.bean.User;

import java.util.List;

public interface UserService {

    List<User> findUsers();

    Result login(String username, String password);
}
