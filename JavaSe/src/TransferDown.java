/**
 * @author goodtime
 * @create 2020-03-04 10:20 下午
 */

class Girl {
    public void smile() {
        System.out.println("girl smile()...");
    }
}

class MMGirl extends Girl {

    @Override
    public void smile() {

        System.out.println("MMirl smile sounds sweet...");
    }

    public void c() {
        System.out.println("MMirl c()...");
    }
}

public class TransferDown {


    public static void main(String[] args) {

        Girl g1 = new MMGirl(); //向上转型
        g1.smile();

        MMGirl mmg = (MMGirl) g1; //向下转型,编译和运行皆不会出错
        mmg.smile();
        mmg.c();


        Girl g2 = new Girl();
//      MMGirl mmg2=(MMGirl)g2; //不安全的向下转型,编译无错但会运行会出错,可以理解为，java不支持向下转型
        //能转型的情况，只是本来g2就是MMGirl，才能强转，否则，就算调用父类和子类共有的方法，也会报错
//      mmg2.smile();  //报错
//      mmg2.c();  //报错
        /*output:
         * CGirl smile sounds sweet...
         * CGirl smile sounds sweet...
         * CGirl c()...
         * Exception in thread "main" java.lang.ClassCastException: com.wensefu.other1.Girl
         * at com.wensefu.other1.Main.main(Girl.java:36)
         */
        if (g2 instanceof MMGirl) {
            MMGirl mmg1 = (MMGirl) g2;
            mmg1.smile();
            mmg1.c();
        }

    }
}
