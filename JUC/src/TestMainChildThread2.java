/**
 * @author goodtime
 * @create 2020-03-13 8:12 下午
 */
public class TestMainChildThread2 {
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
        thread.setDaemon(true);//默认为false，即主线程等子线程结束才结束，如果子线程为守护线程，直接结束
        thread.start();
        System.out.println("main死了");
    }

}

