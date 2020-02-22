import com.sun.corba.se.spi.activation._ServerImplBase;

/**
 * @author goodtime
 * @create 2020-02-15 1:05 上午
 */
class A {
    private int number = 0;


    public synchronized void add() throws InterruptedException {
        while(number != 0){
            this.wait();
        }
            number++;//千万注意，这里和下面不能写在else里面，如果循环次数是复数，会报死锁
            //原因可能是线程恢复后，从上次睡眠的地方开始执行，else会干扰到它的判断
            System.out.println(number);
            this.notifyAll();
    }


    public synchronized void minus() throws InterruptedException {
        while(number != 1){
            this.wait();
        }
            number--;
            System.out.println(number);
            this.notifyAll();

    }

    public int getNumber() {
        return number;
    }
}

public class Producer {
    public static void main(String[] args) {
        A a = new A();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 30; i++) {
                        a.add();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "+1线程").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 30; i++) {
                        a.minus();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "-1线程").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        a.add();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "+11线程").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        a.minus();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "-11线程").start();



    }

}
