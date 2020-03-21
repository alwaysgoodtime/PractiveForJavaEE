import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**软引用堆内存不够会回收
 * @author goodtime
 * @create 2020-03-01 6:06 下午
 */
public class TestSoftReference {
    public static void main(String[] args) {
        Object o1 = new Object();
        SoftReference o2 = new SoftReference(o1);
        o1 = null;
        System.out.println(o2.get());//不清理之前，o2还能引用到o1所指的对象



        //启动时，给java程序配-Xms5m，-Xmx5m,然后new一个10m的大对象，保证gc开始运行，软引用被回收

        try {
            Byte[] a = new Byte[50*1024*1024];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(o1);
            System.out.println(o2);//o2是个引用对象，其中get所得到的对象，才是软引用
            System.out.println(o2.get());//只有get才会清楚，o2本身还有引用
        }


    }
}
