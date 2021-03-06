import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author goodtime
 * @create 2020-02-23 1:59 上午
 */
public class JmsSubscriber1Persistence {
    public static final String ACTIVEMQ_URL = "tcp://47.95.147.76:61616";
    public static final String TOPIC_NAME = "Topic03";
    public static void main(String[] args) {
        //1.创建连接工厂,采用默认账号密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Session session = null;
        TopicSubscriber consumer = null;
        Connection connection = null;
        try {

            System.out.println("我是3号消费者");

            //2.连接工厂，获得connection并访问
            connection = activeMQConnectionFactory.createConnection();
            //2.1在连接上设置消费者id，用来识别消费者
            connection.setClientID("111");

            //3.创建会话，两个参数，第一个是事务，第二个叫签收
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            //4.创建目的地（可以指定队列（queue）还是主题（topic），也可以指定它们的父接口Destination）
            Topic topic = session.createTopic(TOPIC_NAME);
            //5.创建消息的消费者
            consumer = session.createDurableSubscriber(topic, "持久化订阅");
            connection.start();


            //6.设置监听
//            consumer.setMessageListener(new MessageListener() {
//                public void onMessage(Message message) {
//                    if(null != message && message instanceof TextMessage){
//                        TextMessage message1 = (TextMessage) message;
//                        try {
//                            Thread.sleep(1000);
//                            System.out.println("接收消息"+message1.getText());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });

            consumer.setMessageListener((message)->{ if(null != message && message instanceof TextMessage){
                TextMessage message1 = (TextMessage) message;
                try {
                    Thread.sleep(1000);
                    System.out.println("接收消息"+message1.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            });//LAMBDA在1.8以上版本可以支持

            System.in.read();//等待接收消息，保证此进程能等到消费者消费完消息，要不还没消费者消费完消息，连接就都关闭了。只要按下任意键，它接收到消息，就退出了
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                //9.依次关闭producer、session、connection，顺着申请连接，倒着关闭连接
                consumer.close();
                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

        System.out.println("消费成功");
    }
}
