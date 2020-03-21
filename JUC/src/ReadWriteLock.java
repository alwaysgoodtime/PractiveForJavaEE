import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 要求我们能读、也能写,读写锁，保证读的时候可以并发读，写的时候别的线程不能读，也不能写。
 * @author goodtime
 * @create 2020-03-01 11:48 上午
 */

class User2{
    private volatile int age = 10;
    ReentrantReadWriteLock readwriteLock = new ReentrantReadWriteLock();

    public void write(){
        readwriteLock.writeLock().lock();
        age++;
        System.out.println("写ageing");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        readwriteLock.writeLock().unlock();
    }
    public void read(){
        readwriteLock.readLock().lock();
        System.out.println(age);
        readwriteLock.readLock().unlock();
    }
}


public class ReadWriteLock {
    public static void main(String[] args) {
        User2 user2 = new User2();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                user2.read();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                user2.write();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }
}
