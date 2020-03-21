package BIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 *
 * TCP协议的客户端
 * @author goodtime
 * @create 2020-03-02 3:30 下午
 */
public class TCPClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",8000);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("你好".getBytes());//输出流，是相对于客户端，所以客户端的输出流，就是输出到客户端，
        InputStream inputStream = socket.getInputStream();//输入流，也是相对于客户端，所以是服务端相对客户端的输入流

        byte[] a = new byte[1000];

        int read = inputStream.read(a);

        System.out.println(new String(a,0,read));

        socket.close();//关socket，其中的输入输出流就都关了


    }
}
