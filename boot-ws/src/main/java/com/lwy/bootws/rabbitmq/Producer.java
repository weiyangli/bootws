package com.lwy.bootws.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    @Autowired
    private AmqpTemplate template;

    public void send() {
        template.convertAndSend("lwy","hello,rabbit~");
    }
}
