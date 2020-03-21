package BIO;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * TCP协议的服务器，接受客户端的消息
 * @author goodtime
 * @create 2020-03-02 3:35 下午
 */
public class TCPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);//只用写端口即可，ip地址默认为机器ip
        Socket accept = serverSocket.accept();//获取客户端对象
        InputStream inputStream = accept.getInputStream();//相对于服务器的输入流，也就是客户端的输出流

        byte[] a = new byte[100];

        int read = inputStream.read(a);


        System.out.println(new String(a, 0, read));


        OutputStream outputStream = accept.getOutputStream();
        outputStream.write("lalalalalaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes());
        outputStream.write("haha".getBytes());

        accept.close();
        serverSocket.close();

    }
}
