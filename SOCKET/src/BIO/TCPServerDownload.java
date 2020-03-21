package BIO;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPoolManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * TCP协议的服务器，接受客户端的消息
 * @author goodtime
 * @create 2020-03-02 3:35 下午
 */
public class TCPServerDownload {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8002);//只用写端口即可，ip地址默认为机器ip

        //用线程池技术，每个用户建立一个连接，就new一个线程，保证多用户上传速度
        ExecutorService executorService = Executors.newCachedThreadPool();


        //让服务器一直处于监听状态，有一个客户端上传文件，就保存一个文件
        while (true){
            Socket accept = serverSocket.accept();//获取客户端对象，有客户端请求就获取到,从而执行后面的步骤
            System.out.println("来了");

            executorService.execute(new Runnable() {
                @Override
                public void run() {//run不能抛异常，实现的方法也就不能抛，为了多态
                    try {
                        System.out.println(Thread.activeCount());//
                        InputStream inputStream = accept.getInputStream();//相对于服务器的输入流，也就是客户端的输出流
                        OutputStream outputStream = accept.getOutputStream();    //自定义一个文件名，如果写死文件名的话，就会被覆盖
                        String fileName = "/Users/goodtime/Desktop/" + System.currentTimeMillis() +new Random().nextInt(1111111) + ".jpg";//公司名+当前时间+随机值+文件后缀
                        OutputStream a =  new FileOutputStream(fileName);
                        byte[] receive = new byte[10];
                        while(inputStream.read(receive,0,10)!= -1){
                            a.write(receive);
                        }
                        outputStream.write("上传成功".getBytes());
                        inputStream.close();
                        // accept.close();//serverSocket.close();//服务器就一直不关了
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });


        }

    }
}
