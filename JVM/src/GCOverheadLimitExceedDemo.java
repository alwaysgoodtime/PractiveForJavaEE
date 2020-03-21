import java.util.ArrayList;
import java.util.List;

/**
 * 超过98%的时间在做GC，但只回收了不到2%的堆内存
 * @author goodtime
 * @create 2020-03-01 8:22 下午
 */
public class GCOverheadLimitExceedDemo {
    public static void main(String[] args) {
        int i = 0;
        List a = new ArrayList<>();
        try {
            while (true){
                a.add(String.valueOf(++i).intern());
            }
        } catch (Exception e) {
            System.out.println(i);
            e.printStackTrace();
            throw e;
        }
    }
}
