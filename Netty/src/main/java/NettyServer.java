import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author goodtime
 * @create 2020-03-05 4:41 上午
 */
public class NettyServer {
    public static void main(String[] args) throws Exception{
        //1.创建两个线程组，一个处理连接（bossgroup），一个处理业务（workergroup）
        //2.两个都是无限循环
        //3.两个线程组，每个线程组含有的子线程（NioEventLoop）个数默认为电脑cpu核数*2
        //4.子线程都由EventExecutor管理
        //5.可以在新建的时候，给它们传参，传参的数字就是启动线程的个数
        //6.bossgroup给workergroup分配任务的时候，默认是轮询的，比如8个线程，从1到8，然后再到1
        //7.每个子线程NioEventLoop，都有自己的selector，各不一样
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            //用链式编程配置服务器端参数
            bootstrap.group(bossGroup, workerGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NiodSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列等待连接个数
                    //.handler(new NettyServerHandler())//该handler是给bossgroup添加handler
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道初始化对象（匿名对象）
                        //给pipeline设置处理器
                        protected void initChannel(SocketChannel sc) throws Exception {//该handler是给workerGroup添加handler
                            System.out.println("sc的hashcode为"+sc);
                            //可以用一个集合管理SocketChannel（每个客户端的channel都不一样），然后将业务加入到各个channel对应的
                            //NIOEventLoop的taskQueue或scheduleTaskQueue中
                            sc.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("服务器is ready");

            //绑定端口并启动服务器
            final ChannelFuture cf = bootstrap.bind(6668).sync();//绑定一个端口并且同步，生成一个ChannelFuture对象

            //给cf注册监听器，监控我们关心的事件
            cf.addListener(new ChannelFutureListener() {//这里就是异步的监听机制
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(cf.isSuccess()){
                        System.out.println("监听端口6668成功");

                    }else {
                        System.out.println("监听端口6668失败");
                    }
                }
            });


            System.out.println("启动成功");

            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
