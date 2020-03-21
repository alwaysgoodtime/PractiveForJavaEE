import java.util.Random;

/**
 * @author goodtime
 * @create 2020-03-01 8:12 下午
 */
public class JavaHeapSpaceDemo {
    public static void main(String[] args) {
        String a = "haha";

        //-Xms1m -Xmx1m
        while (true){
            a += new Random().nextInt(1111111111)+ new Random().nextInt(22222222);
        }
    }
}
