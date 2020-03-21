/**
 * @author goodtime
 * @create 2020-03-01 10:12 下午
 */
public class ThreadDoubleStart {
    public static void main(String[] args) {
        Thread thread = new Thread();
        thread.start();
        try {
            thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.start();
    }
}
