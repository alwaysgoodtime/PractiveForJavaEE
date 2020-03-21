package groupcheat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author goodtime
 * @create 2020-03-05 10:59 下午
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {//按照字符串来发送

    //每个通道都有自己的管道，也都有自己的handler，所有要定义成static变量，方便共享
    //定义一个channel组，管理所有的channel
    //GlobalEventExecutor.INSTANCE是一个全局的事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static HashMap<String,Channel> channels = new HashMap<>();

    private static  int i = 0;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    //handlerAdded 表示连接建立，一旦连接，第一个被执行
    //将当前channel加入到channlerGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        //加入channlegroup组中
        channelGroup.add(channel);//一定要记得加

        //单对单私聊
        //channels.put((i++)+"",channel);

        //将该客户加入聊天的信息推送到其他在线的客户端
        //该方法会将channelGroup中所有的channel遍历，并发送消息
        //也可以自己用一个list装，然后自己遍历
        channelGroup.writeAndFlush("客户端"+channel.remoteAddress()+"加入聊天" + sdf.format(new Date()) + "\n");
    }


    //断开连接，将xx客户离开信息推送到当前在线的客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channelGroup.writeAndFlush("客户端"+ ctx.channel().remoteAddress()+"离开了\n");
        System.out.println("channelGroup size"+channelGroup.size());//不需要做离线处理了，channelgroup会自动帮我们删除
    }

    //表示channel处于活动状态，提示xx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"上线了\n");
    }

    //表示channel处于不活动状态，表示离线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"离线了\n");
    }

    //转发消息
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

        //获取到当前的channel
        Channel channel = channelHandlerContext.channel();

        //遍历除自己以外的channel
        channelGroup.forEach(ch -> {//ch就是当前遍历到的那个channel
            if(channel != ch){//不是当前的channel，转发消息
            ch.writeAndFlush("客户"+ch.remoteAddress() + "发送了消息" +s+ "\n");
        }else {//回显自己发送的消息给自己
            ch.writeAndFlush("[自己]发送了消息"+ s + "\n");
        }
        });
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
     ctx.close();//关闭通道
    }
}
