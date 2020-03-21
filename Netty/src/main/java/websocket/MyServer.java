package websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author goodtime
 * @create 2020-03-06 12:25 上午
 */
public class MyServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();//默认为8个
        try {

            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))//给bossGroup设置一个netty自带的日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            //因为基于http协议，使用http的编码和解码器
                            pipeline.addLast(new HttpServerCodec());
                            //整个过程是以块方式写，添加ChunkedWriteHandler处理器
                            pipeline.addLast(new ChunkedWriteHandler());

                            //1.http数据在传输过程中是分段的，HttpObjectAggregator，就是可以将多个段聚合
                            //2.这就是为什么，当浏览器发送大量数据时，就会发送多次http请求

                            pipeline.addLast(new HttpObjectAggregator(8192));

                            //说明 1.对应websocket，它的数据是以帧（frame）的形式传递
                            //2.它有六个子类
                            //3.浏览器请求时 现在它的协议是ws（原来是http） ws://localhost:7000/xxx  xxx表示请求的uri，需要
                            //WebSocketServerProtocolHandle来进行映射
                            //4.WebSocketServerProtocolHandler的核心功能是将http协议升级为ws协议，保持长连接
                            //5.http能升级到websocket，是通过一个状态码101
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                            //自定义的handeler，处理业务逻辑
                            pipeline.addLast(new MyTextWebSockerFrameHandler());

                        }

                    });

            //启动服务器
            ChannelFuture sync = bootstrap.bind(7000).sync();
            sync.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
