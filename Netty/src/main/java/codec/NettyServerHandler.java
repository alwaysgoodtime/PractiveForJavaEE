package codec; /**
 * 适配器模式，我们继承了适配器，就可以自定义自己的适配器
 * 不过需要遵守netty的规范，也就是继承它的某个具体的类
 *
 * @author goodtime
 * @create 2020-03-05 4:59 上午
 */
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //方法的作用是读取客户事件
    //1.ChannelHandlerContext,是上下文对象，可以得到管道pipeline，通道channel，地址
    //2.Object msg：客户端发送的数据，封装成了object
    @Override
    public void channelRead(final ChannelHandlerContext cxt, Object msg) throws InterruptedException {
        //这里是监听客户端的任务，如果任务耗时非常长 》 异步执行 》 提交给该channel
        //对应的NIOEventLoop的taskQueue中,这里并不会开启多线程，只是多了个任务队列，耗时任务都放到了
        //NIOEventLoop的taskQueue中，然后一个一个顺序执行
        //Thread.sleep(6000);//假设读业务耗时6秒
        //cxt.writeAndFlush(Unpooled.copiedBuffer("hello~~~",CharsetUtil.UTF_8));//读取完数据（也就是等6秒后，再写回给客户端）



        //解决方案1 用户程序自定义的普通任务

        cxt.channel().eventLoop().execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(6000);
                    cxt.writeAndFlush(Unpooled.copiedBuffer("hello~~~",CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("发生异常"+e.getCause());
                }
            }
        });

        cxt.channel().eventLoop().execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(6000);
                    cxt.writeAndFlush(Unpooled.copiedBuffer("hello！！！！~~~",CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("发生异常"+e.getCause());
                }
            }
        });

        //第二种：用户自定义定时任务 -》该任务是提交到scheduleTaskQueue中，客户端默认先执行taskQueue中的任务，然后才执行定时任务
        //无论写的先后顺序如何



        cxt.channel().eventLoop().schedule(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(6000);
                    cxt.writeAndFlush(Unpooled.copiedBuffer("hello 喵~~~",CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("发生异常"+e.getCause());
                }
            }
        },3, TimeUnit.SECONDS);




        System.out.println("go on ....");

//        System.out.println("context =" + cxt);
//        System.out.println("看看channel和pipeline的关系");
//        Channel channel = cxt.channel();
//        ChannelPipeline pipeline = cxt.pipeline();//管道本质是双向链表，不断出栈入栈
//        //将msg转成netty自带的ByteBuf,它比bytebuffer性能更高
//        ByteBuf byteBuf = (ByteBuf) msg;
//        System.out.println("客户端发送的信息是:"+byteBuf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端地址是:"+cxt.channel().remoteAddress());

    }

    //代表数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //两个方法的合并，将数据写入到缓存区，并刷新，刷新的作用是，把缓存区的数据刷新到管道
        //一般讲，我们对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端",CharsetUtil.UTF_8));
    }

    //发生异常，一般需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

