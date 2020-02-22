import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author goodtime
 * @create 2020-02-15 1:40 下午
 */
class Me implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"laile");
    }
}

class Me2 implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"laile");
        return 1;
    }
}

public class TestRunnable {
    public static void main(String[] args) {
        new Thread(new Me(),"a").start();
        new Thread(new Me(),"b").start();
        System.out.println("结束");
        Me me = new Me();
        new Thread(me,"C").start();
        new Thread(me,"d").start();
        FutureTask<Integer> integerFutureTask = new FutureTask<Integer>(new Me2());
        new Thread(integerFutureTask,"f").start();
        new Thread(integerFutureTask,"e").start();
        Integer integer = null;
        try {
            integer = integerFutureTask.get();
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(integer);

    }
}
