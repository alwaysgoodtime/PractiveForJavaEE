package alwaysgoodtime;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author goodtime
 * @create 2020-02-04 4:49 下午
 */
public class HashMapTest {

    public static void main(String[] args) {

        HashMap hashMap = new HashMap();
    }
}

class AAAA {

    private String a;
    private int b;

    @Override
    public String toString() {
        return "AAAA{" +
                "a='" + a + '\'' +
                ", b=" + b +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AAAA aaaa = (AAAA) o;
        return b == aaaa.b &&
                Objects.equals(a, aaaa.a);
    }

    @Override
//    默认返回是一个int类型，也就是32位数字
    public int hashCode() {
        return Objects.hash(a, b);
    }
}