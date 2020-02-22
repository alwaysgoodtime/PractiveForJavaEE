import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 用callable接口实现
 * @author goodtime
 * @create 2020-02-14 11:18 下午
 */

class MyThread3 implements Callable<Object> {

    @Override
    public Object call() throws Exception {
        System.out.println("来啦");
        return "1";
    }
}


public class SaleTicket3 {
    public static void main(String[] args) {

        Callable a = new MyThread3();

        FutureTask futureTask = new FutureTask(a);

        new Thread(futureTask).start();

        System.out.println(Thread.currentThread().getName()+"来啦");
    }

}

