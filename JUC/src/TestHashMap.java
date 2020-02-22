import java.util.HashMap;

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
    }
}
