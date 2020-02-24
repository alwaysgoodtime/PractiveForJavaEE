package com.god.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author goodtime
 * @create 2020-02-23 2:52 下午
 */
@Service
public class Consumer {

    @Autowired
    JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Consumer consumer = (Consumer) context.getBean("consumer");
        String message = (String)consumer.jmsTemplate.receiveAndConvert();
        System.out.println("消费者收到消息"+message);
    }

}
