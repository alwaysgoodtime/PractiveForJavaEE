import com.sun.tools.corba.se.idl.constExpr.Times;

import java.sql.Time;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author goodtime
 * @create 2020-02-15 2:20 下午
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(4);//模拟资源类，4个车位
        for (int i = 0; i < 7; i++) {
            new Thread(() ->{
                try {
                    semaphore.acquire();//如果线程进来后，就会占用一个停车位
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"抢到了停车位");
                try {
                    TimeUnit.SECONDS.sleep(2);//睡眠的工具类，sleep不释放资源
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"离开停车位");
                semaphore.release();
            },String.valueOf(i)).start();
        }
    }
}
