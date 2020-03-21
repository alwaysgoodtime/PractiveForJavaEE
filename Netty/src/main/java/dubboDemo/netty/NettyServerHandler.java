package dubboDemo.netty;

import dubboDemo.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.HashMap;

/**
 * @author goodtime
 * @create 2020-03-06 8:08 下午
 */
//服务器的handler比较简单
public class NettyServerHandler extends ChannelInboundHandlerAdapter {




    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息，并调用服务器的服务
        System.out.println("msg=" +msg);

        //客户端在调用服务器的api时，需要定义一个协议
        //这里我们规定，每次发消息时，都必须以某个字符串开头: "HelloService#hello#xxx"
        if(msg.toString().startsWith("HelloService#hello#")){

            String s = new HelloServiceImpl().sayHello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(s);//写回服务结果
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
