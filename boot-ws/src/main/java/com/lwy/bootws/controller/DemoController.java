package com.lwy.bootws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/")
    public String helloBootWs() {
        return "login/index";
    }

    @GetMapping("/chat")
    public String chat() {
        return "ws/chat";
    }
}
