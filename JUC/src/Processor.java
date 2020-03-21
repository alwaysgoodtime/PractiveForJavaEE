import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 实现进程的方式1：通过ProcessBuild创建进程
 *
 * @author goodtime
 * @create 2020-03-09 12:57 上午
 */
public class Processor {

    public static void main(String[] args) throws Exception{

        ProcessBuilder pb = new ProcessBuilder("/bin/sh","-c","ifconfig");//创建线程，读取ifconfig
        //传入的命令是个集合

        Process process=pb.start();//进程执行，process就是进程
        process.destroy();//摧毁进程的方法
        boolean alive = process.isAlive();
        System.out.println(alive);
        Thread.sleep(1000);//摧毁进程需要时间，直接调用，一般是没有立刻摧毁的
        System.out.println(process.exitValue());//获取进程的退出标志，为143，说明是c程序
        System.out.println(process.isAlive());
        Scanner scanner = new Scanner(process.getInputStream());//得到进程的输入流
        while(scanner.hasNextLine()){
        System.out.println(scanner.nextLine());
    }
        scanner.close();
    }
}
