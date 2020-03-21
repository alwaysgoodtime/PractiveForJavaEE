import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import sun.jvm.hotspot.debugger.win32.coff.WindowsNTSubsystem;

import java.nio.charset.Charset;

/**
 * @author goodtime
 * @create 2020-03-05 10:19 下午
 */
//Unpooled是工具类，帮助创建Netty的ByteBuf
//byteBuf底层是数组，维护了读指针（readerindex）和写指针（writeindex），所以不需要想Buffer一样进行反转后才能写变成读
//    capacity容量
public class Buf {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            byteBuf.writeByte(i);//writeindex会变化
        }

        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println(byteBuf.getByte(i));//这种读的话，readerindex不会动
        }

        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println(byteBuf.readByte());//readerindex会变化
        }

        ByteBuf byteBuf1 = Unpooled.copiedBuffer("hello,world", Charset.forName("utf-8"));//前面是内容，后面是编码

        if(byteBuf1.hasArray()){
            byte[] array = byteBuf.array();
            System.out.println(new String(array,Charset.forName("utf-8")));//相当于一个解码过程，编码解码一致
        }

        System.out.println(byteBuf1);//实际是36个容量，写了11个字节
        System.out.println(byteBuf1.arrayOffset());//数组偏移量
        System.out.println(byteBuf1.readerIndex());//读指针位置
        System.out.println(byteBuf1.writerIndex());//写指针位置

        System.out.println(byteBuf1.readByte());//读取当前读指针位置的值

        System.out.println(byteBuf1.readableBytes());//可读的长度

        System.out.println(byteBuf1.getCharSequence(1,5, CharsetUtil.UTF_8));
    }



}
