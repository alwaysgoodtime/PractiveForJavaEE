import org.omg.PortableInterceptor.INACTIVE;
import sun.jvm.hotspot.debugger.win32.coff.WindowsNTSubsystem;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author goodtime
 * @create 2020-03-01 6:41 下午
 */
public class TestWeakReference {
    public static void main(String[] args) {
        Object o1 = new Object();
        System.out.println(o1);
        o1 = null;
        Byte[] bytes = new Byte[20*1024*1024];
        bytes = null;
        WeakReference o3 = new WeakReference(o1);
        System.out.println(o3.get());
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.gc();
        System.out.println(o3.get());

    }
}
