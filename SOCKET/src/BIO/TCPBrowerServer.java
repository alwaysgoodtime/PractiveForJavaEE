package BIO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author goodtime
 * @create 2020-03-02 6:07 下午
 */
public class TCPBrowerServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080);

        while (true) {//服务器
            Socket accept = serverSocket.accept();

            ExecutorService executorService = Executors.newCachedThreadPool();

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream inputStream = accept.getInputStream();
                        byte[] bytes = new byte[1024];
                        int len = 0;
//        while((len = inputStream.read(bytes,0,1024))!= -1){
//            System.out.println(new String(bytes,0,len));
//        }
                        //将字节输入流转换为字符缓冲输入流
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                        String s = bufferedReader.readLine();//GET //Users/goodtime/Desktop/IDEA/SOCKET/web/web.html HTTP/1.1

                        //切割获取的字符串

                        System.out.println(s);

                        String[] s1 = s.split(" ");

                        String substring = s1[1];// /Users/goodtime/Desktop/IDEA/SOCKET/web/web.html


                        System.out.println(substring);

                        File file = new File(substring);

                        //本地磁盘IO流，读入对应的HTML文件

                        FileInputStream fileInputStream = new FileInputStream(file);


                        OutputStream outputStream = accept.getOutputStream();

                        //写入Http响应协议头

                        outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());

                        outputStream.write("Content-Type:text/html\r\n".getBytes());

                        outputStream.write("\r\n".getBytes());//这一行也得写，不多换一行，浏览器不识别

                        while ((len = (fileInputStream.read(bytes, 0, 1024))) != -1) {
                            outputStream.write(bytes, 0, len);
                        }
                        accept.close();//最后记得关socket，如果不管，因为浏览器没有提交中断请求，这次请求就会一直都在，浏览器也会不断转圈加载
                    } catch (Exception e) {
                        System.out.println(e.getStackTrace());
                    }
                }

//        serverSocket.close();
            });
        }
    }
}
