package com.god.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author goodtime
 * @create 2020-02-23 3:02 下午
 */
@Service
public class Producer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //从bean中取到producer，注意，默认首字母会变小写
        Producer producer = (Producer) context.getBean("producer");//从容器中拿出Producer的实例对象
//        producer.jmsTemplate.send(new MessageCreator() {
//            public Message createMessage(Session session) throws JMSException {
//                TextMessage message = session.createTextMessage("整合case");
//                return message;
//            }
//        });

        producer.jmsTemplate.send((session)-> {
            TextMessage message = session.createTextMessage("整合case");
            return message;
        });

        System.out.println("生产成功");


    }

}
