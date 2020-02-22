import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池创建线程
 * @author goodtime
 * @create 2020-02-14 11:24 下午
 */
class MyThread4 implements Runnable {

    @Override
    public void run() {
        System.out.println("laila");
    }
}



public class SaleTicket4 {
    public static void main(String[] args) {

        MyThread4 myThread4 = new MyThread4();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor a = (ThreadPoolExecutor)executorService;
        a.setCorePoolSize(10);
        a.setMaximumPoolSize(10);

        a.execute(myThread4);

        System.out.println(Thread.currentThread().getName()+"来啦");

        a.shutdown();//记得关线程池，否则程序一直开着
    }

}

