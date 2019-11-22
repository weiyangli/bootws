package com.lwy.bootws.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class User {
    private int id;
    private String name;
    private String password;
    private int age;
}
