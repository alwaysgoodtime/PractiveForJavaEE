package groupcheat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author goodtime
 * @create 2020-03-05 11:24 下午
 */
public class ChatClient {
    private static String host;
    private static int port;

    public ChatClient(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            //加入自定义的handler
                            pipeline.addLast("myHandler",new ChatClientHandler());
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            //得到channel
            Channel channel = channelFuture.channel();
            System.out.println(channel.localAddress() + "准备好了");
            //客户端需要输入信息，创建一个扫描器
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                //通过channel发送到服务器端
                channel.writeAndFlush(s + "\n");
            }
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        new ChatClient("127.0.0.1",7000).run();
    }



}
