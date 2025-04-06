package com.gitee.rayc.curatorL.simpleTest;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-24
 * @Description: Curator连接ZooKeeper进行基本操作
 * @Version: 1.0
 */
@Slf4j
public class NodeOprTest {
    private CuratorFramework client;
    private static final String CONNECT_STRING = "centos135:2181";

    @Before
    public void before() {
        connect();
        log.info("Connected to {}", CONNECT_STRING);
    }

    @After
    public void after() {
        if (client != null) {
            client.close();
            log.info("Disconnected from {}", CONNECT_STRING);
        }
    }

    public void connect() {
        // 通过工厂获取连接client（CuratorFramework）
        // 使用构造参数构造
//        client = getClientByParams();
        // 使用builder构造
        client = getClientByBuilder();
        client.start();
    }

    private CuratorFramework getClientByParams() {
        // 中间两个参数是默认值，可以用只有两个参数的构造方法
        return CuratorFrameworkFactory.newClient(
                // zookeeper地址，集群用逗号分隔
                CONNECT_STRING,
                // 会话超时时间 毫秒
                60 * 1000,
                // 连接超时时间 毫秒
                40 * 1000,
                // 重连策略，这里是间隙重试的实现，每隔一秒重试，最多重试三次
                new ExponentialBackoffRetry(1000, 3)
        );
    }

    private CuratorFramework getClientByBuilder() {
        return CuratorFrameworkFactory.builder()
                .connectString(CONNECT_STRING)
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(40 * 1000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                // 指明命名空间，默认从该父目录开始执行后续代码
                .namespace("curator-test")
                .build();
    }

    @Test
    public void testCreate() {
        try {
            // 创建并且不指定数据，默认数据为本机（客户端）地址
            // （bash中创建会是null，注意）
            String path = client.create().forPath("/test1");
            // 返回的地址不会带上namespace，依旧是/test1
            log.info("create path: {}", path);

            // 创建并且初始化数据
            client.create().forPath("/test1", "simple".getBytes(StandardCharsets.UTF_8));

            // 设置节点类型 默认持久化|临时|顺序|临时顺序
            // 顺序节点，不同的父节点重新计数
            client.create().withMode(CreateMode.EPHEMERAL).forPath("/test1", "with type e".getBytes(StandardCharsets.UTF_8));

            // 多级创建，设置可自动创建节点
            client.create().creatingParentsIfNeeded().forPath("/test1/hello1", "multi dir".getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            log.error("create path error", e);
        }
    }

    @Test
    public void testGet() {
        try {
            // 查询节点信息
            // bash:         get {path}
            // java api:     getData()
            byte[] bytes = client.getData().forPath("/mall");
            log.info("get data: {}", new String(bytes, StandardCharsets.UTF_8));

            // 查询子节点信息
            // bash:        ls {path}
            // java api:    getChildren()
            List<String> children = client.getChildren().forPath("/mall");
            log.info("get children: {}", children);

            // 查询节点详细信息
            // bash:        ls -s {path}
            // java api:    .storingStatIn(...)
            // 需要新建一个Stat类来保存数据
            Stat stat = new Stat();
            byte[] b = client.getData().storingStatIn(stat).forPath("/mall");
            log.info("get stat: {}", stat);
        } catch (Exception e) {
            log.error("get data error", e);
        }
    }

    @Test
    public void testSet() {
        // 下面是普通的set，但是一般需要根据版本来设置，防止各种并发问题
        try {
            // bash:  set {path} {value}
            Stat stat = client.setData().forPath("/mall", "just test".getBytes(StandardCharsets.UTF_8));
            log.info("set data and get stat: {}", stat);
        } catch (Exception e) {
            log.error("set data error", e);
        }
        try {
            // 创建stat来保存查询到的版本信息
            Stat stat = new Stat();
            String path = "/mall";
            client.getData().storingStatIn(stat).forPath(path);

            int version = stat.getVersion();

            // 乐观锁，根据version更新
            client.setData().withVersion(version).forPath(path, "new data".getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("set data error", e);
        }
    }

    @Test
    public void testDelete() {
        try {
            // bash: delete {path}
            String path = "/mall/t01";
            client.delete().forPath(path);
            log.info("delete path: {}", path);

            // 但是如果节点有子节点，会无法删除报错
            // bash:          deleteAll {path}
            // java api:      deletingChildrenIfNeeded()
            client.delete().deletingChildrenIfNeeded().forPath(path);
            log.info("delete path: {}", path);

            // 保证删除 guaranteed()
            client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);

            // 回调函数
            client.delete().inBackground(new BackgroundCallback() {
                @Override
                public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                    log.info("process result {}", event);
                }
            }).forPath(path);
        } catch (Exception e) {
            log.error("delete path error", e);
        }
    }
}
