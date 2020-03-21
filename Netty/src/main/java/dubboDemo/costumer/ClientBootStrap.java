package dubboDemo.costumer;

import dubboDemo.netty.NettyClient;
import dubboDemo.publicinterface.HelloService;

/**
 * @author goodtime
 * @create 2020-03-06 9:24 下午
 */
public class ClientBootStrap {

    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {

        //创建一个消费者
        NettyClient customer = new NettyClient();

        //创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);

        //通过代理对象，调用服务提供者的方法
        for(;;) {
            String result = service.sayHello("你好，dubbo~");
            System.out.println(result);
            Thread.sleep(1000);
        }

    }
}
