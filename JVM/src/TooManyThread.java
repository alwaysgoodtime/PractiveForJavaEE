/**
 * @author goodtime
 * @create 2020-03-01 11:47 下午
 */
public class TooManyThread {

    //报无法创建本地线程异常，这个不要随便试，直接会让当前进程卡死，需要kill -9 +pid 来强制退出
    public static void main(String[] args) {
        for (int i = 0; ; i++) {
            Thread thread = new Thread();
            thread.start();
        }
    }
}
