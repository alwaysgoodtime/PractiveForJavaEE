package dubboDemo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author goodtime
 * @create 2020-03-06 8:36 下午
 */
public class NettyClient {

    //创建线程池
    private static ExecutorService exector = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());//创建了8个线程，因为我电脑默认是八线程
    //它是可以提交任务的

    private static NettyClientHandler client = null;



    //编写方法使用代理模式，获取一个代理对象

    public Object getBean(final Class<?> serviceClass,final String providerName) {//final用在形参上，表示该方法内部，不能对这个变量进行修改

        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[] {serviceClass},(proxy,method,args) ->{

                    //下面的代码，是客户端每调用一次hello，就会进入到该代码

                    if(client == null){
                        initClient();
                    }

                    //设置要发给服务器端的信息
                    //providerName 协议头，args[0]就是我们客户端调用api sayHello(?） 的参数
                    client.setPara(providerName+args[0]);

                    return exector.submit(client).get();//提交任务后，得到call()方法返回的结果，因为实现
                    //callable（）接口，可以得到返回值
                });
    }

    //初始化客户端
    public static void initClient() {

        client = new NettyClientHandler();

        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)//tcp不延时
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(client);//自定义的处理器,这里让所有的Channel用的都是一个handler
                    }
                });
        try {
            ChannelFuture sync = bootstrap.connect("127.0.0.1", 7000).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
