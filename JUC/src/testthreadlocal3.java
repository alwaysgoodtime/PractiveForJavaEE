import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author goodtime
 * @create 2020-03-13 7:03 下午
 */
public class testthreadlocal3 implements Cloneable{
        public static void main(String[] args){
            testthreadlocal3 p=new testthreadlocal3();
            System.out.println(p);
            Thread t = new Thread(new Runnable(){
                public void run(){
                    ThreadLocal<testthreadlocal3> threadLocal = new ThreadLocal<>();
                    System.out.println(threadLocal);
                    threadLocal.set(p);
                    System.out.println(threadLocal.get());
                    threadLocal.remove();
                    try {
                        threadLocal.set((testthreadlocal3) p.clone());
                        System.out.println(threadLocal.get());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(threadLocal);
                }});
            t.start();
        }
    }