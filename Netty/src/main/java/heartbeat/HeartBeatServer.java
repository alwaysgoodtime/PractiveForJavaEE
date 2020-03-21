package heartbeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author goodtime
 * @create 2020-03-05 11:58 下午
 */
public class HeartBeatServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();//默认为8个

        try{

            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))//给bossGroup设置一个netty自带的日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //加入一个netty提供的IdleStateHandler
                            /*
                            1.本身是netty提供的处理空闲状态的处理器
                            2.三个参数：long readerIdleTime：表示多长时间没有读，就会发送一个心跳检测包检测是否连接
                                       long writerIdleTime:表示多长时间没有写，就会发送一个心跳检测包检测是否连接
                                       long allIdleTime: 表示多长时间没有读写，就会发送一个心跳检测包检测是否连
                             3.当IdleStateEvent触发后，就会传递给管道的下一个handler去处理
                             通过调用（触发）下一个handler的userEventTriggered，在该方法中处理
                             IdleStateEvent(读空闲，写空闲，读写空闲)
                             */
                            //客户端正常离开，我们可以用handlerremoved方法监控，但很多时候，客户端离开，我们是无法感知的

                            pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            //加入一个对空闲检测进一步处理的handler（自定义））
                            pipeline.addLast(new HeartBeatHandler());
                        }
                    });

            //启动服务器
            ChannelFuture sync = bootstrap.bind(7000).sync();
            sync.channel().closeFuture().sync();


        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
