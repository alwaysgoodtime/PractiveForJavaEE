package com.god.activemq;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author goodtime
 * @create 2020-02-23 4:01 下午
 */
@Component
public class MyMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        if(null != message && message instanceof TextMessage){
            TextMessage message1 = (TextMessage) message;
            try {
                System.out.println("收到消息"+message1.getText());
            } catch (JMSException e) {
            }
        }
    }
}
