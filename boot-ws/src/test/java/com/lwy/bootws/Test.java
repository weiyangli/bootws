package com.lwy.bootws;


import com.lwy.bootws.bean.User;
import com.lwy.bootws.utils.Utils;

import java.util.Optional;

public class Test {
    public static void main(String[] args) throws Exception{
        User user =  new User();
        user.setName("lwy");
        Optional<String> opt = Optional.ofNullable(user).map(User::getName).filter(x -> x.equals("lwy"));
        if (opt.isPresent()) {
            System.out.println(String.format("恭喜====》%s", user.getName()));
        } else {
            System.out.println("拉闸");
        }
        System.out.println(Utils.spa512Encode("123456"));
    }
}
