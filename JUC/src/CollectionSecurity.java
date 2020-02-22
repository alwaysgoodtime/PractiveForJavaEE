import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author goodtime
 * @create 2020-02-15 11:26 上午
 */
public class CollectionSecurity {
    public static void main(String[] args) {
        long l = System.currentTimeMillis();//当前时间戳
        String s = UUID.randomUUID().toString().substring(0, 8);
        System.out.println(l);
        System.out.println(s);
        Stack<Object> objects = new Stack<>();
        objects.push("1");
        Object pop = objects.pop();
        System.out.println(pop);

        List a = new CopyOnWriteArrayList<>();//用并发包下的CopyOnWriteArrayList，保证线程安全，效率最高
        List b = new Vector();//原来的容器，线程安全，synchronized
        List c = Collections.synchronizedList(new ArrayList<>());//用collections的工具类保证线程安全
        Set d = new CopyOnWriteArraySet<Object>();//并发包下的Set解决方案
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        Map<Object, Object> e = Collections.synchronizedMap(new HashMap<Object, Object>());//并发包下的HashMap解决方案
    }
}
