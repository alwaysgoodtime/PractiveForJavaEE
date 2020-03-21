import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author goodtime
 * @create 2020-02-19 6:46 下午
 */
public class MapSearch {
    public static void main(String[] args) {


        //存的是一个Object类型的数组，初始值为0，只要一插入，就是10个元素的数组，满了扩容1.5倍
        List a = new ArrayList<>();


        //存的是一个个节点，而非一个值，每个值存入后被包裹两个指针，一前一后，形成双向循环链表，尾插法，插入元素在末尾
        LinkedList<Object> objects = new LinkedList<>();


        //线程安全的map，已被弃用
        Hashtable hashtable = new Hashtable();
    }
}
