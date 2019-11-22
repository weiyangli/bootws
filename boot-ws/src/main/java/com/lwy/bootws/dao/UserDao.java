package com.lwy.bootws.dao;

import com.lwy.bootws.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    List<User> findUsers();

    User findUserByUsername(@Param("username") String username);
}
