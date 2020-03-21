package dubboDemo.provider;

import dubboDemo.publicinterface.HelloService;

/**
 * @author goodtime
 * @create 2020-03-06 7:51 下午
 */
public class HelloServiceImpl implements HelloService {

    private static int count = 0;

    //当有消费方调用该方法时，就返回结果
    @Override
    public String sayHello(String msg) {
        System.out.println("收到客户端消息="+msg);
        //根据msg 返回不同的结果
        if(msg != null){
            return "你好客户端，我收到你的消息【"+msg+"】"+(++count);
        }else{
            return "没收到你的消息";
        }
    }

}
