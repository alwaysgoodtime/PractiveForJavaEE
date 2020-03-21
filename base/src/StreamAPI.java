import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author goodtime
 * @create 2020-03-06 10:41 下午
 */
public class StreamAPI {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("a", "b", "c");

        //返回一个顺序流
        Stream<String> stream = strings.stream();

        //返回一个并行流,并行流的话，从集合和数组总取值的时候，不一定是按顺序取的，因为是并行操作的
        Stream<String> stringStream = strings.parallelStream();

        int[] array = new int[]{1,2,3,4,5};

        IntStream stream1 = Arrays.stream(array);


    }
}
