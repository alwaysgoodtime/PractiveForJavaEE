import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 阻塞消费者版
 * @author goodtime
 * @create 2020-02-23 12:54 上午
 */
public class JmsConsumer2 {
    public static final String ACTIVEMQ_URL = "tcp://47.95.147.76:61616";
    public static final String QUEUE_NAME = "queue01";
    public static void main(String[] args) {
        //1.创建连接工厂,采用默认账号密码

        System.out.println("我是2号消费者");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Session session = null;
        MessageConsumer consumer = null;
        Connection connection = null;
        try {
            //2.连接工厂，获得connection并访问
            connection = activeMQConnectionFactory.createConnection();
            connection.start();

            //3.创建会话，两个参数，第一个是事务，第二个叫签收
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            //4.创建目的地（可以指定队列（queue）还是主题（topic），也可以指定它们的父接口Destination）
            Destination destination = session.createQueue(QUEUE_NAME);
            //5.创建消息的消费者
            consumer = session.createConsumer(destination);
            //6.写个死循环，一直在取消息
            while (true){
                TextMessage receive = (TextMessage) consumer.receive();//有五种消息，这里需要强转一下,我们发的就是textmessage
                //receiveh中可以传个数字，比如传3000，表示等生产者3000毫秒，然后就不等了，不传值就是死等消息
                if(null != receive){
                    System.out.println("收到消息"+receive.getText());//取到消息
                }else {
                    break;//说明消息生产者不生产了，跳出接收
                }
            }
        } catch (JMSException e) {
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

        System.out.println("消息消费成功");
    }
}
