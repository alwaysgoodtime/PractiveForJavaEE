package codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author goodtime
 * @create 2020-03-05 1:17 下午
 */
public class NettyClient {
    public static void main(String[] args) throws Exception {
        //客户端需要一个事件循环组
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            //创建客户端启动对象，不是ServerBootStrap
            Bootstrap bootstrap = new Bootstrap();

            //链式编程，设置相关参数
            bootstrap.group(group)//设置线程组
                    .channel(NioSocketChannel.class)//客户端通道的实现类（反射）
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());//客户端的处理器
                        }
                    });

            System.out.println("客户端 is ready");

            //启动客户端，连接服务器端
            //这里涉及到netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();

            System.out.println("启动成功");

            //给关闭通道进行监听,在关闭通道事件发生后，再进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
}
