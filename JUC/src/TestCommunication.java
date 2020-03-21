/**
 * @author goodtime
 * @create 2020-02-29 11:33 下午
 */

class Cake {

    int i = 0;

    public synchronized void produce() {
        while (i != 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        i++;
        System.out.println(Thread.currentThread().getName()+"生产一个蛋糕");
        this.notifyAll();


    }

    public synchronized void custumer() {
        while (i == 0) {
            try {
                this.wait();//此时拿到锁的是cake，而非线程，所以该释放锁的，也是这个类，而非当前线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        i--;
        System.out.println(Thread.currentThread().getName()+"消费一个蛋糕");
        this.notifyAll();
//        try {//在通知完别的线程后，别当前线程wait，否则到了最后，当前线程会一直等着，而无法退出
//            //原因是，生产者生产完5次蛋糕后，就会结束线程，而消费者消费玩最后一个蛋糕后，就会一直等着
//            this.wait();
//            System.out.println("2");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
}


public class TestCommunication {

    public static void main(String[] args) {
        Cake cake = new Cake();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                cake.custumer();
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                cake.produce();
            }
        }).start();
    }
}
