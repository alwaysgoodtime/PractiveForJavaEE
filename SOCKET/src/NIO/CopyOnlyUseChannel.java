package NIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * 直接通过通道就能拷贝
 * @author goodtime
 * @create 2020-03-04 8:33 下午
 */
public class CopyOnlyUseChannel {
    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("/Users/goodtime/Desktop/a.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/goodtime/Desktop/c.txt");
        FileChannel channel = fileInputStream.getChannel();
        FileChannel channel1 = fileOutputStream.getChannel();
        channel.transferTo(0,channel.size(),channel1);
        channel.close();
        channel1.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
