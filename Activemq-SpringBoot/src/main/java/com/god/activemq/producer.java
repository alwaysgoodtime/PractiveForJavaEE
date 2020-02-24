package com.god.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.network.jms.JmsMesageConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.UUID;

/**
 * @author goodtime
 * @create 2020-02-23 4:27 下午
 */
@Component
@EnableJms
//必须要加这个EnableJms
public class producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired()
    private Queue queue;

    //触发投送，别人调用一次，就投一次
    public void produceMsg(){
        jmsMessagingTemplate.convertAndSend(queue,"******"+ UUID.randomUUID().toString().substring(0,6));
    }

    //间隔定投，每隔3秒投一次,下面的注解，单位为毫秒
    @Scheduled(fixedDelay = 3000)
    public void produceMsgScheduled(){
        System.out.println("我发啦");
        jmsMessagingTemplate.convertAndSend(queue,"定投"+ UUID.randomUUID().toString().substring(0,6));
    }

}
