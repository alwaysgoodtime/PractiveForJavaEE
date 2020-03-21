import java.util.concurrent.atomic.AtomicReference;

/**
 * 循环获取直到成功为止，没有类似wait的阻塞
 * 手动实现自旋锁
 *
 * @author goodtime
 * @create 2020-02-29 10:45 下午
 */


public class SpinLockDemo {

    AtomicReference<Thread> a = new AtomicReference<>(null);//需要借助包装类把Thread包装起来，否则整个流程无法实现


    //上锁
    public void lock() {
        Thread thread = Thread.currentThread();//获取当前调用此方法的线程，此时，上锁的线程，而非对象
        System.out.println("来了");
        while (!a.compareAndSet(null, thread)) {//如果进来时候就是一个空的线程对象，那么马上赋值，并且跳出循环，后面调用此方法的人就得等着了
        }
        System.out.println("上锁");
    }

    //解锁
    public void unlock() {

        Thread thread = Thread.currentThread();
        System.out.println("解锁");
        a.compareAndSet(thread,null);//解锁，只有那个上锁的人能解

    }

    public static void main(String[] args) {

        SpinLockDemo spinLockDemo = new SpinLockDemo();


        new Thread(() -> {
            spinLockDemo.lock();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unlock();
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLockDemo.lock();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unlock();
        }).start();



    }
}
