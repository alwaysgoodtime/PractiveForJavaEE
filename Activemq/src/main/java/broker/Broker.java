package broker;

import org.apache.activemq.broker.BrokerService;

/**
 * 本地自建了一个mini版本的activemq服务器，内嵌到了java程序当中
 * 先执行broker，就启动了一个服务器，然后正常启动生产者和消费者即可
 * @author goodtime
 * @create 2020-02-23 2:24 下午
 */
public class Broker {
    public static void main(String[] args) throws Exception{
        BrokerService brokerService = new BrokerService();
        brokerService.setPopulateJMSXUserID(true);
        brokerService.addConnector("tcp://127.0.0.1:61616");
        brokerService.start();
    }
}
