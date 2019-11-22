package com.lwy.bootws;

import com.lwy.bootws.utils.Utils;

public class Test {
    public static void main(String[] args) throws Exception{
        String passwd = Utils.spa512Encode("123456");
        String passwd2 = Utils.spa512Encode("123456");
        System.out.println(passwd);
        System.out.println(passwd2);
        System.out.println(passwd2.equals(passwd));
    }
}
