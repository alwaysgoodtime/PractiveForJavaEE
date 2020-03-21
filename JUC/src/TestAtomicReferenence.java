import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子引用，对我们自定义的类对象进行包裹，让它成为原子性的。
 * @author goodtime
 * @create 2020-02-29 6:54 下午
 */


class User{
    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    public User() {
    }

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int age;
    private String name;
}

public class TestAtomicReferenence {

    public static void main(String[] args) {

        User user = new User(25,"张三");
        User user2 = new User(24,"李四");

        //将李四视为一个Volatile的共享变量，保证对其操作读写同步，都是原子性的(private volatile V value; 确实是加了volatile)
        AtomicReference atomicReference = new AtomicReference(user);

        System.out.println(atomicReference.get() == user);

        atomicReference.compareAndSet(user,user2);

        String s = atomicReference.get().toString();

        System.out.println(s);

        //此时atomicReference已经是user2的值，不是user的值，就无法set进去了
        atomicReference.compareAndSet(user,user2);

        String s2 = atomicReference.get().toString();

        System.out.println(s2);


        //注意：对atomicReference中的user的操作，并没有影响到原来的user和user2所引用的堆内存中对象的值，
        //不过是对atomicReference这个引用所引用的对象，从堆内存的user变到了user2而已
        System.out.println(user.toString());
        System.out.println(user2 == atomicReference.get());
    }
}
