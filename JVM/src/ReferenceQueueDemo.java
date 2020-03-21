import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 引用队列测试
 *
 * @author goodtime
 * @create 2020-03-01 7:32 下午
 */
public class ReferenceQueueDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        ReferenceQueue queue = new ReferenceQueue();
        WeakReference weak = new WeakReference(o1, queue); //多传入一个引用队列
        o1 = null;
        System.out.println(o1);
        System.out.println(weak.get());
        System.out.println(queue.poll());

        System.gc();

        System.out.println(o1);
        System.out.println(weak.get());
        System.out.println(queue.poll()); //java.lang.ref.WeakReference@610455d6 将回收的软引用放到队列中
    }
}
