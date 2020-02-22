/**
 * @author goodtime
 * @create 2020-02-15 12:56 上午
 */

interface fo{
    void fee();
}

interface haha{
    int ha(int a);
}

public class Lambda {
    public static void main(String[] args) {
       fo f = () -> {
           System.out.println("ha");
       };
       f.fee();


       haha a = (int s) ->{
           System.out.println("heihei");
           return s;
       };

//haha a = (int s) ->s;//这样也行

       a.ha(1);
    }

}
