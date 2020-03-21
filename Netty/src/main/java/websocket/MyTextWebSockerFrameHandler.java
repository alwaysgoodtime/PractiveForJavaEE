package websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * @author goodtime
 * @create 2020-03-06 12:36 上午
 */
//这里TextWebSocketFrame类型，表示一个文本帧（frame）
public class MyTextWebSockerFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

        System.out.println("服务器端收到消息"+ textWebSocketFrame.text());

        //回复消息
        channelHandlerContext.writeAndFlush(new TextWebSocketFrame("服务器时间"+ LocalDateTime.now() + textWebSocketFrame.text()));

    }

    //当web客户端连接后，触发该方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //id表示一个唯一的值，longText是唯一的，shortText不是，可能重复
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asLongText());
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asShortText());

    }

    //客户端下线
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved 被调用" + ctx.channel().id().asLongText());

    }

    //异常发生
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("服务器异常"+cause.getMessage());
        ctx.close();
    }
}
