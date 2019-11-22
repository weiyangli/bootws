package com.lwy.bootws.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 Broker:它提供一种传输服务,它的角色就是维护一条从生产者到消费者的路线，保证数据能按照指定的方式进行传输,
 Exchange：消息交换机,它指定消息按什么规则,路由到哪个队列。
 Queue:消息的载体,每个消息都会被投到一个或多个队列。
 Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来.
 Routing Key:路由关键字,exchange根据这个关键字进行消息投递。
 vhost:虚拟主机,一个broker里可以有多个vhost，用作不同用户的权限分离。
 Producer:消息生产者,就是投递消息的程序.
 Consumer:消息消费者,就是接受消息的程序.
 Channel:消息通道,在客户端的每个连接里,可建立多个channel.
 */

/**
 * 关于rabbitMq个人总结:
 * mq生产者将消息发送至交换机，含有交换机根据指定消息发送模式，和交换及队列的绑定关系，
 * 将消息推送给对应的队列去消费。交换机有常用的有三总模式 direct 点对点模式，交换机消息发送到固定队列，
 * 通过 key 值做唯一路由交互。 fanout 广播模式，不需要 key 值，所有和指定交换机绑定的队列都可接受到消息。
 * 最后一种topic 是最复杂的一种，消息的发布订阅模式，这种模式的 key 值，可以是以 * 或者 # 通配符搭配，对应的
 * 可能是交换机绑定的队列中的一部分。
 */
@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;


    public static final String EXCHANGE_A = "my-mq-exchange_A";
    public static final String EXCHANGE_B = "my-mq-exchange_B";


    public static final String QUEUE_A = "lwy1";
    public static final String QUEUE_B = "lwy2";

    public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
    public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";

    public RabbitConfig() {
    }

//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setAddresses(this.host);
//        connectionFactory.setPort(this.port);
//        connectionFactory.setUsername(this.username);
//        connectionFactory.setPassword(this.password);
//        return connectionFactory;
//    }
//
//    @Bean
//    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
//        return new RabbitAdmin(connectionFactory);
//    }
//
//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public RabbitTemplate rabbitTemplate() {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(this.connectionFactory());
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//        return rabbitTemplate;
//    }

    @Bean
    public Queue QueueA() {
        return new Queue(QUEUE_A);
    }

    @Bean
    public Queue QueueB() {
        return new Queue(QUEUE_B);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_A);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_B);//配置广播路由器
    }

    @Bean
    Binding bindingExchangeMessage(@Qualifier("QueueA") Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(ROUTINGKEY_A);
    }
    @Bean
    Binding bindingExchangeMessage2(@Qualifier("QueueB") Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(ROUTINGKEY_B);
    }

    @Bean
    Binding bindingExchangeMessage3(@Qualifier("QueueA") Queue queueMessage, FanoutExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange);
    }
    @Bean
    Binding bindingExchangeMessage4(@Qualifier("QueueB") Queue queueMessage, FanoutExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange);
    }
}
