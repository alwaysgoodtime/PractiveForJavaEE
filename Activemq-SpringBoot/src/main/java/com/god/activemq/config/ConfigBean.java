package com.god.activemq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 * @author goodtime
 * @create 2020-02-23 4:22 下午
 */
@Component
public class ConfigBean {

    @Value("${myqueue}")
    //从yml中注入
    private  String myQueue;

    @Bean // 类似<bean id = "" class = "">
    public Queue queue() {
        return new ActiveMQQueue(myQueue);
    }
}
