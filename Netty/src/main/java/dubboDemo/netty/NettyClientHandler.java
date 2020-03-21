package dubboDemo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author goodtime
 * @create 2020-03-06 8:18 下午
 */
//必须实现Callable接口
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {


    private ChannelHandlerContext context;//上下文
    private String result;//将来调用后返回的结果
    private String para;//客户端调用方法时，传入的参数

    //被代理对象调用，发送数据给服务器 -》wait -》 等待被唤醒(3)(5)
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(para);//我们发送给服务器的那个msg
        //等待服务器返回结果
        wait();//等待channelRead 方法获取到服务器返回的结果
        //它和channelRead是互斥的，都会得到当前handler，保证和另一个方法只有一个在执行
        return result;
    }

    //与服务器的连接创建后，就会被调用,它是第一个被调用的（1 括号为执行步骤）
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;//因为我们在其他方法会使用到ctx
    }

    //收到服务器的数据后，调用方法


    //(4)
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify();//唤醒等待的线程,其实就是this.notify

    }

    //异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.getMessage();
        ctx.close();
    }

    //设置参数(2)
    void setPara(String para){
        this.para = para;
    }
}
