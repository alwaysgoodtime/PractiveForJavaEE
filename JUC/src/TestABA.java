import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 用AtomicStampedReference来解决ABA问题，也是乐观锁的一种重要实现方式
 * @author goodtime
 * @create 2020-02-29 7:42 下午
 */
public class TestABA {
    public static void main(String[] args) {

        AtomicInteger i = new AtomicInteger(100);

        new Thread(new Runnable() {
            @Override
            public void run() {
                i.compareAndSet(100,101);
                i.compareAndSet(101,100);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }//让这个线程睡一会儿，保证ABA完成
                i.compareAndSet(100,199);
            }
        }).start();

        while (Thread.activeCount() > 2){//保证其他两个线程都执行完毕
            Thread.yield();
        }

        System.out.println(i.get());

        ////////////解决ABA问题

        AtomicStampedReference aa = new AtomicStampedReference(100,1);//初始版本号为1，每次加1


        new Thread(new Runnable() {
            @Override
            public void run() {
                int stamp = aa.getStamp();
                System.out.println(stamp);
                try {//让它睡1秒，保证另一个线程的版本号是1
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                aa.compareAndSet(100,101,aa.getStamp(),aa.getStamp()+1);
                aa.compareAndSet(101,100,aa.getStamp(),aa.getStamp()+1);
                System.out.println(aa.getStamp());

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int stamp = aa.getStamp();//这是先执行，肯定是初始的版本号1
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }//让这个线程睡一会儿，保证ABA完成
                aa.compareAndSet(100,199,stamp,stamp+1);
                System.out.println(aa.getReference());
            }
        }).start();

    }
}
