package com.lwy.bootws.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Comsumer {

    @RabbitListener(queues="lwy1")    //监听器监听指定的Queue
    public void processC(String str) {
        System.out.println("Receive:"+str);
    }

    @RabbitListener(queues="ws2")    //监听器监听指定的Queue
    public void processD(String str) {
        System.out.println("Receive:"+str);
    }

    @RabbitListener(queues="ws3")    //监听器监听指定的Queue
    public void processE(String str) {
        System.out.println("Receive:"+str);
    }
    @RabbitListener(queues="ws4")    //监听器监听指定的Queue
    public void processF(String str) {
        System.out.println("Receive:"+str);
    }
}
