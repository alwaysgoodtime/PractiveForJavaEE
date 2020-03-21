import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * @author goodtime
 * @create 2020-03-01 6:58 下午
 */
class AA{
    int a = 1;
}
public class TestWeakHashMap {
    public static void main(String[] args) {
        HashMap<Object,String> hashMap = new HashMap<>();
        Integer key  = new Integer(1);
        AA aa = new AA();
        aa.a = 1;
        hashMap.put(aa,"hashmap");
        key  = 2;//这个是让key指向别的堆内存中的对象了，所以取不到值了

        System.out.println(hashMap.get(key));
        aa.a = 2;//这个是aa所指向的对象的值改变，hashmap对应保存的key（aa）也变化了，所以还是能取到值

        System.gc();

        System.out.println(hashMap.get(aa));



        //////////////////////////////////////

        WeakHashMap<Object,Object> hashMap2 = new WeakHashMap<>();
        Integer key2  = new Integer(1);
        hashMap2.put(key2,"hashmap3");
        hashMap2.put(null,"1");
        //hashMap2.put("222",key2);//加上这一行后，原来应该被清空的key2，现在key2还是保留着，可能是多了这个引用吧
        key2 = null;
        System.out.println(hashMap2);
        //key2因为变成null，就会被垃圾回收，内存就腾出来了，hashMap2中原来key2所存的键值对也就被回收了。
        System.gc();
        System.out.println(hashMap2);

    }
}
