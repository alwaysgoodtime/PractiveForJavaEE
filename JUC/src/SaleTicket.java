/**
 *方法一：继承Thread创建多线程
 * @author goodtime
 * @create 2020-02-14 9:08 下午
 */

class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+"卖"+i);
        }
    }
}


public class SaleTicket {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.setName("李四");
        myThread.start();
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+"卖"+i);
        }
    }

}
