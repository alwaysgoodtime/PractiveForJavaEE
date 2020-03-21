import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author goodtime
 * @create 2020-03-13 7:03 下午
 */
public class TestThreadLocal2 {
     public static void main(String[] args) throws InterruptedException {
            new AAA().start();//普通变量是不会共享的
            new AAA().start();
            new AAA().start();
            new AAA().start();
            new AAA().start();
            new A().start();//静态变量会共享的，因为是A这个类本身发生了变化
            new A().start();
            new A().start();
            new A().start();
            new A().start();
        }

    static class A extends Thread {
        static List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        static ThreadLocal<List<Integer>> threadLocal = new ThreadLocal<List<Integer>>() {
            @Override
            protected List<Integer> initialValue() {
                return list;
            }
        };

        @Override
        public void run() {
            List<Integer> threadList = threadLocal.get();
            threadList.add(threadList.size());
            System.out.println(threadList.toString());
        }

    }
}

class AAA extends Thread {
     List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
     ThreadLocal<List<Integer>> threadLocal = new ThreadLocal<List<Integer>>() {
        @Override
        protected List<Integer> initialValue() {
            return list;
        }
    };

    @Override
    public void run() {
        List<Integer> threadList = threadLocal.get();
        threadList.add(threadList.size());
        System.out.println(threadList.toString());
    }

}