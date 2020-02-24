import org.apache.activemq.ActiveMQConnectionFactory;
import sun.security.util.ResourcesMgr;

import javax.jms.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 监听消费者版
 * @author goodtime
 * @create 2020-02-23 12:54 上午
 */
public class JmsConsumer {
    public static final String ACTIVEMQ_URL = "tcp://47.95.147.76:61616";
    public static final String QUEUE_NAME = "queue01";
    public static void main(String[] args) {
        //1.创建连接工厂,采用默认账号密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Session session = null;
        MessageConsumer consumer = null;
        Connection connection = null;
        try {

            System.out.println("我是一号消费者");

            //2.连接工厂，获得connection并访问
            connection = activeMQConnectionFactory.createConnection();
            connection.start();

            //3.创建会话，两个参数，第一个是事务，第二个叫签收
            session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
            //4.创建目的地（可以指定队列（queue）还是主题（topic），也可以指定它们的父接口Destination）
            Destination destination = session.createQueue(QUEUE_NAME);
            //5.创建消息的消费者
            consumer = session.createConsumer(destination);

            //6.设置监听
            consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    if(null != message && message instanceof TextMessage){
                        TextMessage message1 = (TextMessage) message;
                        try {
                            System.out.println("接收消息"+message1.getText());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            System.in.read();//等待接收消息，保证此进程能等到消费者消费完消息，要不还没消费者消费完消息，连接就都关闭了。只要按下任意键，它接收到消息，就退出了
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                //9.依次关闭producer、session、connection，顺着申请连接，倒着关闭连接
                consumer.close();
                session.commit();
                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

        System.out.println("消费成功");
    }
}
