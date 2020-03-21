import sun.misc.VM;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @author goodtime
 * @create 2020-03-01 9:12 下午
 */
public class DirectBufferMemoryDemo {
    public static void main(String[] args) {
        long l = VM.maxDirectMemory();//java虚拟机可以用的最大本地内存，这个和java可以支配的最大内存不同，是指java可以直接操作的本地内存
        //java可以支配的最大内存，是指运行时实际用的堆内存的大小（我们有两个幸存者区，实际运行中只会用一个survivor，另一个限制），MaxHeapSize，是指运行时数据区中java堆的最大内存限制
        System.out.println(l/1024/1024 + "MB");

        //我们要用nio，传参后，需要6mb的本地内存，但可以设置运行时，本地内存为5m，就会抛出错误DirectBufferMemory
        //-XX:MaxDirectMemory=5m
        ByteBuffer.allocateDirect(6*1024*1024);
    }
}
