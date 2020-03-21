import java.util.Random;

/**
 * @author goodtime
 * @create 2020-02-14 1:55 下午
 */
public class Param {
    public static void main(String[] args) {
        //获得本机可用的处理器（虚拟线程数，我的电脑是四核，开启了虚拟线程，一个核心又拆成了两个线程，所以是8线程）
        //Runtime，这个就是把运行时数据区，抽象成了一个对象，叫做Runtime类
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(Runtime.getRuntime().maxMemory()/1024/1024 + "MB");//返回虚拟机试图使用的最大内存量-Xmx-1个survivor
        System.out.println(Runtime.getRuntime().totalMemory()/1024/1024 + "MB");//返回虚拟机的初始最小内存总量-Xms


        //实际情况下，最大内存Xmx和初始内存Xms弄的一样大。理由：避免GC和应用程序争抢内存，导致程序抖动和停顿


        String str = "bababa";
        while (true){//这种是不停死循环导致OOM
            str = str + new Random().nextInt(888888888)+new Random(999999999);//现在这个好像不能报OOM了
//            byte[] a = new byte[1024*1024*1024];//可以直接new一个超大的数组，占据内存过大，直接报OOM

        }


    }
}
