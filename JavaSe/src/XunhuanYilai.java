import java.util.HashMap;

public class XunhuanYilai{

    public static void main(String[] args) throws Exception {
        System.out.println(new A());
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
    }

}

class A {
    public A() {
        new B();
    }
}

class B {
    public B() {
        new A();
    }
}
