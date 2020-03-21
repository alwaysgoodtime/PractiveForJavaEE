package http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author goodtime
 * @create 2020-03-05 3:35 下午
 */
public class TestServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            //用链式编程配置服务器端参数
            bootstrap.group(bossGroup, workerGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NiodSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列等待连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    .childHandler(new TestServerInitializer());

            System.out.println("服务器is ready");

            final ChannelFuture  cf = bootstrap.bind(8000).sync();

            //给cf注册监听器，监控我们关心的事件
            cf.addListener(new ChannelFutureListener() {//这里就是异步的监听机制
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(cf.isSuccess()){
                        System.out.println("监听端口8000成功");

                    }else {
                        System.out.println("监听端口8000失败");
                    }
                }
            });

            cf.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


        }

}
