package dubboDemo.provider;

import dubboDemo.netty.NettyServer;

/**
 * 启动一个服务提供者
 * @author goodtime
 * @create 2020-03-06 7:58 下午
 */
public class ServerBootstrap {

    public static void main(String[] args) {

        //启动服务
        NettyServer.startServer("127.0.0.1",7000);

    }
}
