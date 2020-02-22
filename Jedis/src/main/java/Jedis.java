import redis.clients.jedis.Transaction;

import java.util.HashMap;
import java.util.List;

/**
 * 最普通的建立连接与事务
 * @author goodtime
 * @create 2020-02-20 1:33 下午
 */
public class Jedis {
    public static void main(String[] args) {

        //建立连接
        redis.clients.jedis.Jedis jedis = new redis.clients.jedis.Jedis("127.0.0.1",6379);

        System.out.println(jedis.ping());

        jedis.set("key","haha");
        String key = jedis.get("key");
        System.out.println(key);



        jedis.hset("sdsd","a","b");
        System.out.println(jedis.hget("sdsd", "a"));

        jedis.lpush("a","1","2","3","4");
        String a = jedis.lpop("a");
        System.out.println(jedis.rpop("a"));
        List<String> lrange = jedis.lrange("a", 0, -1);
        System.out.println(lrange);
        System.out.println(a);

        jedis.watch("haha");//如果监控这个key，并且在建立监控之后，在事务开启之前被改动过（比如被别的进程改动m），事务就无法执行

        Transaction multi = jedis.multi();

        multi.set("haha","hu");
        multi.decr("haha");

        multi.exec();

        jedis.set("haha","8");


        Transaction multi2 = jedis.multi();//执行一次事物后，watch锁就过期了，所以这次事务可以执行

        multi2.set("haha","lullal");
        multi2.decr("haha");//使命redis的事务不能保证一致性，冤头债主，只有它未执行

        multi2.exec();


        jedis.unwatch();//unwatch可以取消监控，让事务正常执行



        System.out.println(jedis.get("haha"));
    }
}
