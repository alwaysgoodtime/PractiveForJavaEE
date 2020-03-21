package dubboDemo.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author goodtime
 * @create 2020-03-06 7:59 下午
 */
public class NettyServer {

    //这是编程的一种思想，不要暴露我们的方法，可以重载多个构造器和方法，来调用我们自己的方法
    public static void startServer(String hostname,int port){
        startServer0(hostname,port);
    }




    //编写一个方法，完成对NettyServer的初始化和启动，一般netty在初始化和启动的时候，方法后面会写0
    private static void startServer0(String hostname,int port){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{

            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup,workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast(new StringDecoder());
                                pipeline.addLast(new StringEncoder());//编码解码器
                                pipeline.addLast(new NettyServerHandler());//自定义处理器
                            }
                        });
            ChannelFuture sync = serverBootstrap.bind(hostname, port).sync();
            sync.channel().closeFuture().sync();

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}