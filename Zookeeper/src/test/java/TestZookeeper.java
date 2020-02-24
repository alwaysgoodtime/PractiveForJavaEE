import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 简单调用zookeeper
 * @author goodtime
 * @create 2020-02-24 3:13 下午
 */
public class TestZookeeper {
    //访问三个zookeeper集群的客户端端口号
    private static final String CONNECT = "47.95.147.76:2180,47.95.147.76:2181,47.95.147.76:2182";

    //超时时间设置
    private static final int TIMEOUT = 2000;

    private static ZooKeeper zkclient = null;

    public static void main(String[] args) throws Exception {

        //1.拿到操作zookeeper集群的对象
        zkclient = new ZooKeeper(CONNECT, TIMEOUT, new Watcher() {
                //监控器，
                // 获取子节点"/"下的节点，并监控节点的变化
                public void process(WatchedEvent watchedEvent) {

                    List<String> children = null;
                    try {
                        children = zkclient.getChildren("/", true);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for (String c:children
                    ) {
                        System.out.println(c);
                    }

                }
            });

        //判断节点是否存在,后面也可以监听，这里是false，没有监听

        Stat exists = zkclient.exists("/", false);
        System.out.println(exists);


        //让主线程不结束，保证监听器可以一直监听
        Thread.sleep(Long.MAX_VALUE);

    }

    //创建节点方法
    public static void createNode() throws Exception{
        //创建节点
        //四个参数，节点，节点存的数据（不存数据，节点无法创建成功），节点的权限（希望被谁看到），节点是否持久化和带序号
        String s = zkclient.create("/goodtime", "happyday".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(s);
    }


    
}
