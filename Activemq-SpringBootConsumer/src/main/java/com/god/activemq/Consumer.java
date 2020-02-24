package com.god.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

/**
 * 消费者监听版本
 *也
 * @author goodtime
 * @create 2020-02-23 5:23 下午
 */
@Component
public class Consumer {

    //springboot的监听，通过此注解实现
    @JmsListener(destination = "${myqueue}")
    public void receive(TextMessage textMessage) throws Exception {
        System.out.println("消费者收到消息" + textMessage.getText());
    }

}
