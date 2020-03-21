package groupcheat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import javax.xml.soap.SAAJMetaFactory;

/**
 * @author goodtime
 * @create 2020-03-05 10:40 下午
 */
public class ChatServer {
   private int port;//监听端口

   public ChatServer(int port){
       this.port = port;
   }

   //编写run方法，处理客户端请求
   public void run() throws Exception{


           //创建两个线程组
           EventLoopGroup bossGroup = new NioEventLoopGroup(4);
           EventLoopGroup workerGroup = new NioEventLoopGroup(8);
       try {
           ServerBootstrap serverBootstrap = new ServerBootstrap();//配置启动

           //配置channel
           serverBootstrap.group(bossGroup,workerGroup)
                   .channel(NioServerSocketChannel.class)
                   .option(ChannelOption.SO_BACKLOG,128)
                   .childOption(ChannelOption.SO_KEEPALIVE,true)
                   .childHandler(new ChannelInitializer<SocketChannel>() {
                       @Override
                       protected void initChannel(SocketChannel socketChannel) throws Exception {
                           ChannelPipeline pipeline = socketChannel.pipeline();
                           //向pipeline加入解码器
                           pipeline.addLast("decoder",new StringDecoder());
                           //编码器
                           pipeline.addLast("encoder",new StringEncoder());
                           //自定义的处理器
                           pipeline.addLast(new ChatServerHandler());

                       }
                   });

           ChannelFuture sync = serverBootstrap.bind(port).sync();

           //监听关闭
           sync.channel().closeFuture().sync();
       } catch (InterruptedException e) {
           bossGroup.shutdownGracefully();
           workerGroup.shutdownGracefully();
       }


   }

    public static void main(String[] args) throws Exception {
        new ChatServer(7000).run();
    }
}
