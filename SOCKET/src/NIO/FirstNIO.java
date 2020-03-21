package NIO;

import com.sun.tools.classfile.ConstantPool;
import sun.security.ssl.Debug;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * 入门体验
 * @author goodtime
 * @create 2020-03-03 1:41 上午
 */
public class FirstNIO {
    public static void main(String[] args) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/goodtime/Desktop/a.txt");
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(20);
        buffer.put("来学习吧".getBytes());
        buffer.flip();//写转读
        channel.write(buffer);//buffer中没有数据的位上，字节为0，而写的流，是不会写值为0的数据的，默认其为空

//        while (buffer.hasRemaining()){
//            fileOutputStream.write(buffer.get());
//        }//传统BIO也能读

        //写出以后再读回来
        FileInputStream fileInputStream = new FileInputStream("/Users/goodtime/Desktop/a.txt");
        FileChannel channel1 = fileInputStream.getChannel();

        buffer.flip();//读转写


        int read = channel1.read(buffer);

        System.out.println(new String(buffer.array()).trim());//输出到控制台的时候，buffer中的为0的字节也会输出
        System.out.println(new String(buffer.array()).substring(0,read/3));//因为一个汉字三个字节，除以3正好，有
        //英文就不行了


        fileInputStream.close();
        fileOutputStream.close();

    }
}
