package com.lwy.bootws;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@SpringBootApplication
@MapperScan("com.lwy.bootws.dao")
public class BootWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootWsApplication.class, args);
        System.out.println("项目已启动!");
    }

}
