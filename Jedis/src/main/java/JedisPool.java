import redis.clients.jedis.Jedis;

/**
 * @author goodtime
 * @create 2020-02-20 2:22 下午
 */
public class JedisPool {
    public static void main(String[] args) {

        redis.clients.jedis.JedisPool jedisPool = JedisPoolUtils.getJedisPool();
        Jedis resource = null;//定义到try中就变成了代码块中的变量，它的作用域只有在try中生效， 要放到外面来，才能让最后finally中得到
        try {
            resource = jedisPool.getResource();
            resource.set("1","8");
            System.out.println(resource.get("1"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisPoolUtils.release(jedisPool,resource);
            jedisPool.close();
        }

    }
}
