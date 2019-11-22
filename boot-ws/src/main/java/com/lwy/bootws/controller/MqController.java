package com.lwy.bootws.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MqController {

    @Autowired
    private AmqpTemplate template;

    @GetMapping("/api/message/send")
    @ResponseBody
    public String sendMessage(@RequestParam String message) {
        //template.send("ws2",new Message(message.getBytes(), new MessageProperties()));
        template.convertAndSend("ws.fanout","",message);//参数2忽略
        return "success";
    }
}
