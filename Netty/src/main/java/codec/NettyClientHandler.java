package codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author goodtime
 * @create 2020-03-05 1:29 下午
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    //当通道就绪时，就会触发该方法
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client"+ctx);
        //客户端发送数据
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello~~~",CharsetUtil.UTF_8));
    }

    //当通道有读取事件时，就会触发该方法
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务端的信息是" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址是" + ctx.channel().remoteAddress());
    }

    //出现异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();//关闭通道
    }
}
