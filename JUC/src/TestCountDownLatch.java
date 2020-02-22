import java.util.concurrent.CountDownLatch;

/**
 * @author goodtime
 * @create 2020-02-15 2:01 下午
 */
public class TestCountDownLatch {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println("第"+Thread.currentThread().getName()+"个学生离开");
                countDownLatch.countDown();
            },String.valueOf(i)).start();//这个不能直接用i拼串，这是由于lambda表达式的限制，其中元素必须是final类型的
        }//这样定义完线程后，记得start()
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("看门人离开");
    }
}
