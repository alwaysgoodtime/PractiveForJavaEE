/**
 *
 * 指令重排测试
 * @author goodtime
 * @create 2020-02-29 4:43 下午
 */

class My {
    volatile int a = 0;
    volatile boolean b = false;

    public void  method1() {
        a = 1;
        b = true;
        //a和b加上volatile，就可以禁止指令重排，保证一定先执行a = 1，后执行b = true
    }

    public void method2() {
        while (!b){
        }
            a = a + 5;
            if(a == 5) {
                System.out.println(a);
            }

    }
}

public class TestOrder {

    public static void main(String[] args) {

        My s = new My();

        for (int i = 0; i < 1000; i++) {

            new Thread(new Runnable() {
                @Override
                public void run () {
                    s.method2();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run () {
                    s.method1();
                }
            }).start();

        }


    }
}
