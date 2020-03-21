import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author goodtime
 * @create 2020-02-16 5:29 下午
 */
public class TestHashMap {
    public static void main(String[] args) {
        HashMap<Object, Object> map = new HashMap<>();//hashmap取不存在的key值时，默认value为null
        Object aaa = map.get("aaa");
        map.put("aaa",null);
        System.out.println(aaa);
        map.put(null,"a");
        System.out.println(map.get(null));//map可以存key为null的值

        Map<Object, Object> objectObjectMap = Collections.synchronizedMap(map);

        Vector<Object> objects = new Vector<>();

        CopyOnWriteArrayList a = new CopyOnWriteArrayList();

        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();

        a.indexOf(1);

        a.remove(1);
    }
}
