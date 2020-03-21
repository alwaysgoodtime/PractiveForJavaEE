package NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author goodtime
 * @create 2020-03-04 10:59 下午
 */
public class NIOClient {
    public static void main(String[] args) throws Exception{
        //1.得到一个网络通道
        SocketChannel channel = SocketChannel.open();

        //2.设置非阻塞
        channel.configureBlocking(false);

        //3.提供服务器的ip和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",7766);


        //4.连接服务器
        if(!channel.connect(inetSocketAddress)){//这里是连接不成功
            System.out.println("等一下");
            while (!channel.finishConnect()){//这里表示连接完成，如果连接成功，就会跳出循环，连接成功
                System.out.println("客户端不理你，你先干点别的");
            }
        }
        //5.连接成功，创建buffer，发送数据
        String str = "hello 123！！！";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());//直接根据字节数组的大小
        //包裹进buffer中去，buffer不用指定大小，它的大小就是字节数组的大小，wrap包装

        //发送数据，将buffer数据写入channel
        channel.write(buffer);
        System.in.read();

        channel.close();


    }
}
