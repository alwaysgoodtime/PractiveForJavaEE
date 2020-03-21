import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *
 * @author goodtime
 * @create 2020-03-01 12:19 下午
 */

public class TestConcurrentHashMap {
    public static void main(String[] args) {

        CopyOnWriteArraySet set = new CopyOnWriteArraySet();
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();

        boolean add = set.add(null);




        Map<Object, Object> hashMap = new ConcurrentHashMap<>();

//        hashMap.put("1",null); 报错
//        hashMap.put(null,"1"); 报错

        Set set2 = new HashSet();
        set2.add(null);


//        Set set4 = new TreeSet();
//        set4.add(null);//报错，底层为红黑树

        Set set5 = new LinkedHashSet();
        set5.add(null);


        List list = new LinkedList();
        list.add(null);

        List list2 = new Vector();
        list2.add(null);

        Hashtable<Object, Object> hashtable = new Hashtable<>();

//        hashtable.put(null,1);报错
//        hashtable.put(1,null);报错

        TreeMap mao5 = new TreeMap();
//        mao5.put(null,1); 报错
//        mao5.put(1,null); 报错

        new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    hashMap.put(i,i);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            new Thread(() -> {
                for (int i = 0; i < 20; i++) {
                    Object o = hashMap.get(i);
                    System.out.println(o);
                }
            }).start();
            new Thread(() -> {
                for (int i = 10; i < 20; i++) {
                    hashMap.put(i,i);
                }
            }).start();



        //遍历concurrenthashmap,就是让它传给map的父类引用，从而用父类的方法遍历它

//        Set<Object> objects = hashMap.keySet();
//
//        for (Object a: objects
//             ) {
//            Object o = hashMap.get(a);
//            System.out.println(o);
//
//        }


//        HashMap hashMap1 = new HashMap();
//        hashMap1.put(1,2);
//        hashMap1.put(2,4);
//        hashMap1.put(3,6);
//        hashMap1.put(4,8);
//
//
//        Set set1 = hashMap1.entrySet();
//
//        Iterator iterator = set1.iterator();


//        while (iterator.hasNext()){
//            Map.Entry s = (Map.Entry) iterator.next();
//            System.out.println(s.getKey());
//            System.out.println(s.getValue());
//        }
//
//        for (Object a:set
//             ) {
//            Object o = hashMap1.get(a);
//            System.out.println(o);
//        }

    }
}
