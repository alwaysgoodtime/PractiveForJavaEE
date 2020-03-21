package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO服务器端
 * 如果客户端下线，会不断抛读事件
 * 而且缓存区也没有处理好
 *
 * @author goodtime
 * @create 2020-03-04 9:55 下午
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {

        //1.创建ServerSocketChannel，通过open方法
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //2.得到一个Selector对象
        Selector selector = Selector.open();

        //3.绑定端口，在服务器监听
        serverSocketChannel.socket().bind(new InetSocketAddress(7766));

        //4.设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //5.把serverSocketChannel注册到selector 关心的事件为连接时间OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //6.循环等待客户端连接
        while (true) {
            //等待1s，没有事件发生，则返回
            System.out.println("当前的selector的数量为:"+selector.keys().size());
            if (selector.select(1000) == 0) {//没有事情发生
                System.out.println("等1秒，无连接");
                continue;
            }

            //走到这里，说明有时间发生
            //1.如果返回的值>0,就获取到相关的selectionKey集合
            //2.selector.selectedKeys() 返回关注事件的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            //遍历Set
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            //遍历其中的事件，看看具体的事件是什么
            while (keyIterator.hasNext()) {
                System.out.println("当前的selector的数量为:"+selector.keys().size());
                System.out.println("活跃的连接数量"+selector.selectedKeys().size());

                SelectionKey key = keyIterator.next();
                //根据key 对应的通道发生的时间做相应处理
                if (key.isAcceptable()) {//说明是连接事件
                    //给客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //说明，在BIO中，我们也是通过accept获取socket，该方法在NIO中也是阻塞的
                    //只是，当前情况下，我们的选择器已经捕捉到了连接事件，所以只要一accept，就
                    //创建好了socketChannel，原来的BIO，我们是不知道何时来连接，只能一直傻傻地阻塞

                    System.out.println("客户端" + socketChannel.hashCode() + "注册成功");

                    socketChannel.configureBlocking(false);//要设置服务器与客户端的channel也是非阻塞的！！！！！


                    //将channel注册到selector，关注事件为OP_READ,相当于selector监控它的读请求
                    //同时给socketChannel关联一个Buffer，也就是读取到它的数据时，暂存的地方
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }
                if (key.isReadable()) {//发生了读取事件
                    //通过key 反向获取到对应的socketchannel
                    SocketChannel channel = (SocketChannel) key.channel();//返回的默认是SelectableChannel
                    // 不是因为SocketChannel继承了SocketChannel，所以能向下强转，而是因为，返回的本身就是一个SocketChannel
                    //对象，只是因为为了通用性，返回了SocketChannel而已。（向下强转必须先向上强转）

                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();//这样就获取了和当前key绑定的线程的那个Buffer

                    System.out.println("from client" + new String(byteBuffer.array()).trim());//说明此时还没有数据

                    channel.read(byteBuffer);//用当前与用户建立的通道，将当前用户发过来的数据读到缓冲区中

                    System.out.println("from client" + new String(byteBuffer.array()).trim());

                    byteBuffer.clear();

                    //key.cancel();如果不写这行，就得捕获异常，因为客户端关闭后，会不断给服务端发送一个读事件

                }

                //手动从集合中移除当前的selectionkey，防止重复操作
                keyIterator.remove();


            }


        }
    }

}
