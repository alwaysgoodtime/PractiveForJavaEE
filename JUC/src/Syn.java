import java.util.concurrent.ThreadPoolExecutor;

/**
 * 测试同步
 * @author goodtime
 * @create 2020-02-14 11:32 下午
 */

class Ticket implements Runnable{
    int ticket = 30;

    @Override
    public void run() {
        synchronized (this){//可以用this这个类来充当锁，这个对象是唯一的，此时是同步代码块
            for (int j = 0; ticket > 0; j++) {
                System.out.println("卖了第"+ ticket-- +"票"+"还剩票数"+ticket);
            }
        }
//        synchronized (Ticket.class){//也可以用它的类来充当所，这也是个对象，而且唯一
//            for (int j = 0; ticket > 0; j++) {
//                System.out.println("卖了第"+ ticket-- +"票"+"还剩票数"+ticket);
//            }
//        }
    }
}

public class Syn {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        Thread thread = new Thread(ticket,"wang");
        Thread thread1   = new Thread(ticket,"zhang");
        thread.start();
        thread1.start();
    }
}
