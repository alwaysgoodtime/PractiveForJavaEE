import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;



/**
 * 消息生产者，注意导的包，是org.apache.activemq和javax.jms下的包，spring和java本身也有对应的包
 * 注意选择
 * @author goodtime
 * @create 2020-02-23 12:26 上午
 */
public class JmsProducer {
    public static final String ACTIVEMQ_URL = "tcp://47.95.147.76:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) {

        //1.创建连接工厂,采用默认账号密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Session session = null;
        MessageProducer producer = null;
        Connection connection = null;
        try {
            //2.连接工厂，获得connection并访问
            connection = activeMQConnectionFactory.createConnection();
            connection.start();

        //3.创建会话，两个参数，第一个是事务，第二个叫签收
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（可以指定队列（queue）还是主题（topic），也可以指定它们的父接口Destination）
            Destination destination = session.createQueue(QUEUE_NAME);
        //5.创建消息的生产者
            producer = session.createProducer(destination);
        //6.发三条消息到MQ的队列里面
            for (int i = 0; i < 10; i++) {
                //7.放消息
                TextMessage textMessage = session.createTextMessage("msg" + i);//可以理解为一个字符串
                //8.用生产者发消息
                producer.send(textMessage);
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            try {
                //9.依次关闭producer、session、connection
                producer.close();
                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

        System.out.println("消息发布到activeMQ成功");


    }
}
