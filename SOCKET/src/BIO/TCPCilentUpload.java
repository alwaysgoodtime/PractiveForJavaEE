package BIO;

import java.io.*;
import java.net.Socket;
import java.util.Random;

/**
 * 客户端文件上传
 * @author goodtime
 * @create 2020-03-02 4:58 下午
 */
public class TCPCilentUpload {
    public static void main(String[] args) throws IOException {


        InputStream inputStream = new FileInputStream("/Users/goodtime/Desktop/19300001357258133412489354717.jpg");

        Socket socket = new Socket("127.0.0.1",8002);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream1 = socket.getInputStream();


        byte[] a = new byte[20];

        while (inputStream.read(a,0,20) != -1){//注意：是不等于-1，如果读完了，就会返回-1
            outputStream.write(a);
        }

        socket.shutdownOutput();//上传完文件后，必须手动结束，要不会结束上传，服务器端没有读取到结束标记，会一直阻塞读，
        //而客户端得不到返回结果，也会一直阻塞读

        int len = 0;//注意，要加这个len，否则有可能返回乱码，因为最后一次返回，20个字节并不是全放满了
        while ((len = inputStream1.read(a,0,20))!= -1){
            System.out.println(new String(a,0,len));
        }

        inputStream.close();
        socket.close();

    }
}
