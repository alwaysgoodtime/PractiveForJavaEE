import java.io.IOException;
import java.util.Scanner;

/**
 * 通过Runtime.getRuntime().exec创建进程
 * @author goodtime
 * @create 2020-03-09 1:21 上午
 */
public class Processor2 {
    public static void main(String[] args) throws IOException

    {
        //底层还是ProcessBuilder方法，不过默认是将其start，源码见下
        Process process = Runtime.getRuntime().exec("/bin/sh -c ifconfig");//通过虚拟机的实例Runtime的exec方法来创建进程
        Scanner scanner = new Scanner(process.getInputStream());
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
        scanner.close();
        }
}


// public Process exec(String[] cmdarray, String[] envp, File dir)
//        throws IOException {
//        return new ProcessBuilder(cmdarray)
//            .environment(envp)
//            .directory(dir)
//            .start();
//    }