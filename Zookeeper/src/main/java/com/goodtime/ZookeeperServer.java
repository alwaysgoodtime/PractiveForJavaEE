package com.goodtime;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @author goodtime
 * @create 2020-02-24 5:09 下午
 */
public class ZookeeperServer {

    //访问三个zookeeper集群的客户端端口号,无论是服务器，还是客户端，都是连的客户端，只是一个负责读，一个负责写
    private static final String CONNECT = "47.95.147.76:2180,47.95.147.76:2181,47.95.147.76:2182";

    //超时时间设置
    private static final int TIMEOUT = 2000;

    private static ZooKeeper zookeeper = null;

    public static void main(String[] args) throws Exception {
      //1.连接服务器集群
        ZookeeperServer testZookeeper = new ZookeeperServer();
        testZookeeper.getConnnect();

        //2.注册,传的值是每个服务器的hostname,作用就是写节点
        testZookeeper.register("103");

        //3.业务处理，睡觉是为了让进程不结束
        testZookeeper.business();

    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    private void register(String hostname) throws Exception {
        //暂时的节点，只要服务器下线，该服务器上的节点就消失
        zookeeper.create("/servers/server",hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    private void getConnnect() throws IOException {
        zookeeper = new ZooKeeper(CONNECT, TIMEOUT, new Watcher() {
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }
}
