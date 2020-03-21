package heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author goodtime
 * @create 2020-03-06 12:13 上午
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    /**
     *
     * @param ctx 上下文
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
         if(evt instanceof IdleStateEvent){

             //将evt向下转型
             IdleStateEvent event = (IdleStateEvent)evt;
             String eventType = null;
             switch (event.state()){
                 case READER_IDLE:
                     eventType = "读空闲";
                     break;
                 case WRITER_IDLE:
                     eventType = "写空闲";
                     break;
                 case ALL_IDLE:
                     eventType = "读写空闲";
                     break;
             }

             System.out.println(ctx.channel().remoteAddress()+"超时事件"+eventType);
             System.out.println("服务器做相应处理");

             ctx.channel().close();
         }
    }
}
