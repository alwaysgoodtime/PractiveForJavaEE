
/**
 * @author goodtime
 * @create 2020-03-01 8:03 下午
 */
public class StackOverflowErrorDemo {
    public static void main(String[] args) {
        loop();
    }

    private static void loop() {
        loop();//递归调用以后，java栈溢出，是错误Error，不是异常
    }
}
