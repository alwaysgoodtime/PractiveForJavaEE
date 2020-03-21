/**
 * @author goodtime
 * @create 2020-02-14 9:22 下午
 */
/**
 *方法一：实现Runnable创建多线程
 * @author goodtime
 * @create 2020-02-14 9:08 下午
 */

class MyThread2 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+"卖"+i);
        }
    }
}


public class SaleTicket2 {
    public static void main(String[] args) {
        MyThread2 myThread2 = new MyThread2();

        Thread me = new Thread(myThread2);

        me.start();


        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+"卖"+i);
        }
    }

}
