import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 自己手写一个JedisPool连接池，使用懒汉式的单例模式，延迟加载
 * @author goodtime
 * @create 2020-02-20 2:04 下午
 */
public class JedisPoolUtils {

    //私有化构造器
    private JedisPoolUtils(){};

    //单例的那个实例,变成静态变量，因为getJedisPool是静态方法，也不同在new个工具对象了，直接用类就可以调用
    //被volatile修饰的变量不会被本地线程缓存，对该变量的读写都是直接操作共享内存,防止出现两个线程，都读到了这个jedispool
    //为空到了自己的线程缓存中，结果一个在同步方法块中new了一个jedispool，并刷新到了内存中，另一个还不知道别人已经new过了
    //自己线程中的jedispool还是空的，会再new一次，说白了，就是因为volatile保证了可见性
    private static volatile JedisPool  jedisPool = null;

    //提供连接池
    public static JedisPool getJedisPool(){
        if(jedisPool == null){
            synchronized (JedisPoolUtils.class){
                if(jedisPool == null){//再读一次，防止刚才两个线程都进到if中，然后一个new完，另一个不加判断（因为已经判断过）就又进来了
                    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();//JedisPool线程池配置
                    jedisPoolConfig.setMaxIdle(32);
                    jedisPoolConfig.setMaxTotal(100);
                    jedisPool = new JedisPool(jedisPoolConfig,"127.0.0.1",6379);
                }
            }
        }
        return jedisPool;
    }

    //提供释放连接池中连接的方法
    public static void release(JedisPool jedisPool,redis.clients.jedis.Jedis jedis){
        if(jedis != null){//其实底层已经判断过一次了
            jedisPool.returnResourceObject(jedis);
        }
    }


}
