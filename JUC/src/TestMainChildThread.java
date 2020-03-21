/**
 * join
 * @author goodtime
 * @create 2020-03-13 8:12 下午
 */
public class TestMainChildThread {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("我快死了");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("我真死了");
        });
        thread.start();
        try {
            thread.join();//join()方法的作用是阻塞当前线程（这里是main线程），直到调用join()的线程(也就是这里的thread)结束销毁，或者指定阻塞时长，若线程没停止但是超时，取消阻塞
            //join会释放当前线程所持有的锁
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main死了");
    }

}

