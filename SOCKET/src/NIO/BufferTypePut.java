package NIO;

import java.nio.ByteBuffer;

/**
 * @author goodtime
 * @create 2020-03-04 8:39 下午
 */
public class BufferTypePut {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        byteBuffer.putChar('a');
        byteBuffer.putInt(-4);
        byteBuffer.flip();
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getInt());//如果类型不一致，有可能报错，不一定报错
        int remaining = byteBuffer.remaining();
        System.out.println(remaining);

        ByteBuffer byteBuffer1 = byteBuffer.asReadOnlyBuffer();
        byteBuffer1.put((byte)1);//会报异常，buffer显示为只读，所以不能再读了
    }
}
