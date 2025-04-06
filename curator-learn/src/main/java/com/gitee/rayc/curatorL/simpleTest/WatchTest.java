package com.gitee.rayc.curatorL.simpleTest;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-24
 * @Description: watch监听测试
 * @Version: 1.0
 */
@Slf4j
public class WatchTest {
    private CuratorFramework client;
    private static final String CONNECT_STRING = "192.168.202.135:2181";

    @Before
    public void before() {
        client = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_STRING)
                .sessionTimeoutMs(60_000)
                .connectionTimeoutMs(40_000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        client.start();
        log.info("Connected to {}", CONNECT_STRING);
    }

    @After
    public void after() {
        if (client != null) {
            client.close();
            log.info("Disconnected from {}", CONNECT_STRING);
        }
    }

    @Test
    public void testNodeCache() throws Exception {
        // 使用计数器让线程不停
        CountDownLatch countDownLatch = new CountDownLatch(1);

        // 创建cache
        NodeCache nodeCache = new NodeCache(client, "/curator-test/node-cache");
        // 设置监听回调
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                // 获取节点现在的数据
                ChildData currentData = nodeCache.getCurrentData();
                // 注意，bash创建节点不赋值默认null，删除之后也是null，注意判空
                if (currentData != null) {
                    String dataNow = new String(currentData.getData());
                    log.info("监听到节点改变, 现在节点的值为: {}", dataNow);
                } else {
                    log.info("节点内容为null或节点不存在");
                }
            }
        });
        // 开启监听
        // 参数为开启时是否加载缓存，默认false
        nodeCache.start(true);

        // 阻塞
        countDownLatch.await();
    }

    @Test
    public void testChildrenCache() throws Exception {
        // 使用计数器让线程不停
        CountDownLatch countDownLatch = new CountDownLatch(1);

        // 创建cache
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/curator-test/children-cache", true);
        // 添加监听回调
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
                // 可以获取到类型 一般关注 CHILD_ADDED, CHILD_UPDATED, CHILD_REMOVED
                // 可以根据不同类型执行不同操作
                log.info("子节点变化, 变化类型: {}", event.getType());
                if (event.getData() != null) {
                    // 删除节点的类型时也可以获取到data，获取的是删除前的内容
                    log.info("子节点 {} 内容为: {}", event.getData().getPath(), event.getData().getData());
                } else {
                    log.info("本次data为空");
                }
            }
        });
        // 开启监听
        pathChildrenCache.start();

        // 阻塞
        countDownLatch.await();
    }

    // TreeCache基本和PathChildrenCache一致，不重复编写了

    @Test
    public void testCuratorCache() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        // 上面三种cache都被荒废了，使用CuratorCache替代，CuratorCache能够监听自己和子节点
        CuratorCache cache = CuratorCache.build(client, "/curator-test/curator-cache");
        cache.listenable().addListener(new CuratorCacheListener() {
            @Override
            public void event(Type type, ChildData oldData, ChildData newData) {
                // 不同情况下 oldData 和 newData 都有可能为null
                // 多级创建/删除节点会有多个event，创建从外到内（oldData都是null），删除从内到外（newData都是null）
                // start的时候如果监听的节点（包括子节点）存在会各触发一次NODE_CREATED，就和多级创建节点一样
                log.info("\n\n[{}]\n|<--  {}\n|-->  {}", type, oldData, newData);
            }
        });
        cache.start();

        countDownLatch.await();
    }
}
