import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试condition
 * @author goodtime
 * @create 2020-02-29 11:33 下午
 */

class Cake2 {

    int i = 0;

    ReentrantLock lock = new ReentrantLock();

    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();


    public void produce() {
        lock.lock();
        while (i != 0) {
            try {
                condition1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        i = i + 2;
        System.out.println(Thread.currentThread().getName()+"生产两个蛋糕");
        condition2.signal();
        lock.unlock();
    }

    public void custumer() {
        lock.lock();
        while (i != 2) {
            try {
                condition2.await();//此时拿到锁的是cake，而非线程，所以该释放锁的，也是这个类，而非当前线程
                //注意！！！ 调的是await方法，而非wait方法
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        i = i - 1;
        System.out.println(Thread.currentThread().getName()+"消费一个蛋糕");
        condition3.signal();
        lock.unlock();
    }

    public void custumer2() {
        lock.lock();
        while (i != 1) {
            try {
                condition3.await();//此时拿到锁的是cake，而非线程，所以该释放锁的，也是这个类，而非当前线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        i--;
        System.out.println(Thread.currentThread().getName()+"消费一个蛋糕");
        condition1.signal();
        lock.unlock();
    }
}

//现在是一个生产者，两个消费者，要精确通知
public class TestCondition {

    public static void main(String[] args) {
        Cake2 cake = new Cake2();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {//注意：循环次数写错，也可能让程序一直不停
                cake.custumer();
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                cake.produce();
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                cake.custumer2();
            }
        }).start();
    }
}
