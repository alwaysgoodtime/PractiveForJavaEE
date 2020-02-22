import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author goodtime
 * @create 2020-02-15 10:45 上午
 */
class Source{
    private int number = 1;
    private ReentrantLock lock2 = new ReentrantLock();
    private Condition condition1 = lock2.newCondition();//condition1相当于代表线程，可以精确控制要唤醒哪个线程
    private Condition condition2 = lock2.newCondition();
    private Condition condition3 = lock2.newCondition();

    public void print5(){
        lock2.lock();
        try {
            while(number != 1){
                condition1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("a打印"+i);
            }
            number = 2;//修改标志位
            condition2.signal();//只唤醒condition2状况下的线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock2.unlock();
        }
    }
    public void print10(){
        lock2.lock();
        try {
            while(number != 2){
                condition2.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("b打印"+i);
            }
            number = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock2.unlock();
        }
    }
    public void print15(){
        lock2.lock();
        try {
            while(number != 3){
                condition3.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("c打印"+i);
            }
            number = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock2.unlock();
        }
    }
}

public class SignalOrder {
    public static void main(String[] args) {
        Source source = new Source();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                source.print5();
            }
        },"a").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                source.print10();
            }
        },"a").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                source.print15();
            }
        },"a").start();
    }
}
