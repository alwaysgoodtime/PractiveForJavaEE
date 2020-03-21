
/**
 * ￿
 * @author goodtime
 * @create 2020-03-01 5:51 下午
 */
public class StrongRef {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = o1;
        o1 = null;//让o1引用为null，从而去除强引用，被gc回收
        System.gc();//显式调用gc，但gc线程一般都是并非立即执行，而且gc一般都是fullGC
        // 就和我们让Thread.start,run方法不会立即执行一样,好像让主线程睡一会儿，也不一定保证会回收
        System.out.println(o1);
        System.out.println(o2);


    }
}
