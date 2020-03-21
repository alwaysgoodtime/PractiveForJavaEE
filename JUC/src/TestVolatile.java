import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author goodtime
 * @create 2020-02-29 2:59 下午
 */


//两个线程之间无可见性
class Mydata{
    int number = 0;
    public void  add(){
        this.number = 60;
    }


}

//有可见性,不保证完整性
class Mydata2{
    volatile int number = 0;
    AtomicInteger integer =  new AtomicInteger();

    public void  add(){
        this.number = 60;
    }
    public void addPlus(){
        number++;
    }

    public void automicAdd(){
        integer.getAndIncrement();//取得值，然后加1，被java包装成了原子操作
    }
}



//1.实现可见性
//2.实现原子性：不可分割、完整性、也即某个线程正在做某个具体业务时，
//中间不可以被加塞或者被分割，需要整体完整
//要么同时成功，要么同时失败（Volatile不能保证）
public class TestVolatile {

    public static void main(String[] args) {

        //线程操纵资源类


        //资源类
        Mydata2 mydata = new Mydata2();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mydata.add();
                System.out.println(Thread.currentThread().getName()+mydata.number);

            }
        }).start();


        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        mydata.addPlus();
                        mydata.automicAdd();
                    }
                    System.out.println(Thread.currentThread().getName()+mydata.integer);
                    System.out.println(Thread.currentThread().getName()+mydata.number);

                }
            }).start();
        }


        while (mydata.number == 0){
            //如果没人通知main，main线程会一直在这循环等待
        }

        //这个设置，是为了让主线程在等所有线程操作完后，再输出number，睡5秒肯定够，但不好
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //另一种设置方法,多线程控制上面线程都操作完的方法，默认只有守护线程GC和main线程，所以如果有别的线程，线程数一定大于2
        while(Thread.activeCount() > 2){
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName()+mydata.number);
        System.out.println(Thread.currentThread().getName()+mydata.integer);



    }
}
