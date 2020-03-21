import java.util.concurrent.locks.ReentrantLock;

/**
 * 1.证明synchronized和 ReentrantLock都是递归锁
 * 2.和synchronized锁方法时一样，ReentrantLock，一般new默认的锁头是对象本身（this）
 * 如果是个静态的成员变量，锁的就是这个类本身了（Tel.class）
 * @author goodtime
 * @create 2020-02-29 10:15 下午
 */

class Tel{

    ReentrantLock lock =  new ReentrantLock();//锁头是Tel的实例对象

    static ReentrantLock lock2 =  new ReentrantLock();//锁头是Tel这个类



    public void  sms(){
        email();
        lock.lock();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"10086");

        lock.unlock();
    }

    public static void email(){
        lock2.lock();
        System.out.println("哈哈@99");
        lock2.unlock();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class TestReenterLock {
    public static void main(String[] args) {
        Tel tel = new Tel();
        new Thread(new Runnable() {
            @Override
            public void run() {
                tel.sms();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                tel.email();
            }
        }).start();
    }


}
