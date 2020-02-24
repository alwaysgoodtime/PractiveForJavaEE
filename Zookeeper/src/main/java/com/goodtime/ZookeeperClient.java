package com.goodtime;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author goodtime
 * @create 2020-02-24 5:24 下午
 */
public class ZookeeperClient {
    //访问三个zookeeper集群的客户端端口号,无论是服务器，还是客户端，都是连的客户端，只是一个负责读，一个负责写
    private static final String CONNECT = "47.95.147.76:2180,47.95.147.76:2181,47.95.147.76:2182";

    //超时时间设置
    private static final int TIMEOUT = 2000;

    private static ZooKeeper zookeeper = null;

    public static void main(String[] args) throws Exception {

        ZookeeperClient zookeeperClient = new ZookeeperClient();

        //1.获取集群连接
        zookeeperClient.getConnnect();

        //2.注册监听
        zookeeperClient.geiRegister();

        //3.业务逻辑处理
        zookeeperClient.business();
    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    private void geiRegister() throws Exception {
        List<String> children = zookeeper.getChildren("/servers", true);

        //存储节点服务器名称
        ArrayList<String> arrayList = new ArrayList();

        for (String c: children
             ) {
            byte[] data = zookeeper.getData("/servers/"+c, false, null);
            arrayList.add(new String(data));
        }

        //将所有服务器打印到控制台
        System.out.println(arrayList);
    }

    private void getConnnect() throws IOException {
        zookeeper = new ZooKeeper(CONNECT, TIMEOUT, new Watcher() {
            public void process(WatchedEvent watchedEvent) {

                //如果注册监听不写在这里，就不会监听节点的变化
                try {
                    geiRegister();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
