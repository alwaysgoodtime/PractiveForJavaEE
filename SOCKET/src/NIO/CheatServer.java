package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 群聊的客户端
 *
 * @author goodtime
 * @create 2020-03-05 12:25 上午
 */
public class CheatServer {


    //定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6666;


    //构造器
    //初始化工作
    public CheatServer() {

        try {
            //得到选择器
            selector = Selector.open();
            //得到ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            listenChannel.bind(new InetSocketAddress(PORT));//绑定端口

            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //注册ServletSocketChannel
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //转发功能


    //监听功能,上线提示
    public void listen() {

        try {
            while (true) {
                int count = selector.select(2000);
                if (count > 0) {//说明有时间发生
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //取出selectionkey
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel channel = listenChannel.accept();
                            channel.configureBlocking(false);//设置阻塞要放在注册的前面，否则会抛错误
                            channel.register(selector, SelectionKey.OP_READ);//有人上线了，得到管道，注册读监听
                            //提示某人上线了
                            System.out.println(channel.getRemoteAddress() + "上线");
                        }
                        if (key.isReadable()) {//通道发生read时间，也即通道是可读状态，说明有数据过来了
                            //处理读的方法
                            readData(key);
                        }


                        iterator.remove();//移除当前selectionkey，防止重复遍历

                    }
                } else {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
        }
    }

    //从channel中读取数据到缓存区buffer中，并调用转发方法
    private void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            //取得关联的channel
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = channel.read(buffer);//将通道的数据读到缓存区中

            //根据count做处理，大于0，说明读取到了值
            if (count > 0) {
                //把缓存区的数据转成字符串
                String msg = new String(buffer.array()).trim();
                //输出该消息
                System.out.println("from 客户端" + msg);
                //向其他客户端转发消息，专门写一个方法处理
                sendOtherClients(msg, channel);
            }else {//小于等于0，说明是有服务器下线了
                //一个socketchannel下线，会抛异常，并发起一个读操作，注意捕获异常，否则会一直发
                try {
                    System.out.println(channel.getRemoteAddress() + "离线了...");
                    //取消注册
                    key.cancel();
                    //关闭通道
                    channel.close();
                } catch (IOException f) {
                    f.printStackTrace();
                }
            }
        } catch (IOException e) {//捕获异常，不过我写到了else中

        }

    }

    private void sendOtherClients(String msg, SocketChannel self) {//接受消息和当前的通道
        //遍历所有注册到selector上的SocketChannel，并排除self
        try {
            for (SelectionKey key : selector.keys()) {//注意，遍历的是keys，而非selectedkeys

                //通过key，取出对应的socketchannel
                Channel channel = key.channel();//这里简化了一下，直接转化成了channel
                if (channel instanceof SocketChannel && channel != self) {//!= 直接比较的就是地址
                    SocketChannel socketChannel = (SocketChannel) channel;
                    ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes());
                    socketChannel.write(wrap);//给其他通道写数据
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }


    public static void main(String[] args) {
        //启动客户端
        CheatServer cheatServer = new CheatServer();

        cheatServer.listen();

    }
}
