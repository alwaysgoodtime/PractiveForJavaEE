package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author goodtime
 * @create 2020-03-05 1:04 上午
 */
public class CheatClient {
    //定义相关的属性
    private final String HOST = "127.0.0.1";//服务器的ip
    private final int PORT = 6666;//服务器端口
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    //构造器，完成初始化工作
    public CheatClient() throws IOException {
        try {
            selector = Selector.open();
            //连接服务器
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            //设置非阻塞
            socketChannel.configureBlocking(false);
            //将客户端通道注册到selector中
            socketChannel.register(selector, SelectionKey.OP_READ);//注意，这里是拿socketChannel注册
            //说明两种通道都可以注册selector，并且监听发过来的读请求
            //得到username
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username + "is ok ...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //向服务器发送消息
    public void sendInfo(String info) {
        info = username + "说 ：" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //接受服务器消息
    public void readInfo() {
        try {
            int select = selector.select();//不写数字的话，这里就会一直阻塞
            if (select > 0) {//有可以用的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        //得到相关的通道
                        SocketChannel channel = (SocketChannel) key.channel();
                        //得到一个Buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //读取
                        channel.read(buffer);
                        //把读到的缓存区的数据转成字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());//去掉头尾空格和其他符号

                        iterator.remove();//移除当前的selectionkey，防止重复遍历

                    }
                }
            }else {
                // System.out.println("没有可用的通道。。。。");

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }


    public static void main(String[] args) throws Exception {
        CheatClient cheatClient = new CheatClient();

        //启动一个线程，每隔3秒，读取服务器发送的数据

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    cheatClient.readInfo();
                    try {
                        Thread.currentThread().sleep(3000);//每隔3秒读一次数据
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //发送数据给服务器端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            cheatClient.sendInfo(s);//发送数据
        }
    }
}
