import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author goodtime
 * @create 2020-02-29 5:04 下午
 */

class Singleton{

    volatile static private Singleton singleton;

    private Singleton(){
       System.out.println(Thread.currentThread().getName());
   }//记得先私有化构造器，否则单例对象随便创建


   public static Singleton get(){
       if(singleton == null){
           synchronized (Singleton.class){
               if(singleton == null){
                   singleton = new Singleton();
               }
           }
       }
       return singleton;
   }


}

public class Danli {
    public static void main(String[] args) {
        //单机版
//        Singleton singleton = Singleton.get();
//        Singleton singleton1 = Singleton.get();
//        System.out.println(singleton == singleton1);

        //多线程版本
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1; j++) {
                        System.out.println("1");
                        Singleton singleton2 = Singleton.get();
                        Singleton singleton3 = Singleton.get();
                       System.out.println(singleton2 == singleton3);
                    }

                }
            }).start();
        }

    }
}
