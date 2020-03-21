package groupcheat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author goodtime
 * @create 2020-03-05 11:35 下午
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<String> {


    //拿到服务器端的信息，打印
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s.trim());
    }
}
