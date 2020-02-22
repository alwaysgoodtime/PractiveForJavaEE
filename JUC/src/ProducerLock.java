import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock、condition.await、condition.signal,组合使用
 * @author goodtime
 * @create 2020-02-15 1:05 上午
 */
class AA {

    private int number = 0;
    private ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void add() throws InterruptedException {
        lock.lock();
        try {
            while (number != 0) {
                condition.await();
            }
            number++;//千万注意，这里和下面与if搭配时，不能写在else里面，如果循环次数是复数，会报死锁
            //原因可能是线程恢复后，从上次睡眠的地方开始执行，else会干扰到它的判断
            System.out.println(number);
            lock.unlock();

            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void minus() throws InterruptedException {
        lock.lock();
        try {
            while (number != 1) {
                condition.await();
            }
            number--;
            System.out.println(number);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public int getNumber() {
        return number;
    }
}

public class ProducerLock {
    public static void main(String[] args) {
        A a = new A();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 30; i++) {
                        a.add();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "+1线程").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 30; i++) {
                        a.minus();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "-1线程").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        a.add();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "+11线程").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        a.minus();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "-11线程").start();


    }

}
