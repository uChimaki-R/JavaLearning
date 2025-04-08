package com.gitee.rayc.curatorL.dynamicProp.manager;

import com.gitee.rayc.curatorL.dynamicProp.anno.MarkRefresh;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-27
 * @Description: 添加client监听，实现热更新
 * @Version: 1.0
 */
@Slf4j
@Configuration
// 实现BeanPostProcessor接口，接手所有bean对象，扫描带有自定义注解的字段
public class RefreshManager implements BeanPostProcessor {
    // 扫描bean时用类名+字段名构造path映射到bean，更新时根据被修改的path找到bean并更新字段
    private final Map<String, Object> pathToBean = new HashMap<String, Object>();

    @Resource
    private CuratorFramework client;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 对于所有bean，找到有自定义注解的字段，构造zookeeper上的节点（path）加入到map中，用字段映射到这个bean，后续收到zookeeper发送的更新信息就可以实时更新
        String className = bean.getClass().getName();
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(MarkRefresh.class)) {
                continue;
            }
            String fieldName = field.getName();
            // 构造节点路径
            String path = "/" + className + "/" + fieldName;
            try {
                // 获取bean的初始值
                // 这里默认字段是String类型的，不然还得序列化反序列化等
                String value = (String) field.get(bean);
                // 创建节点（临时节点）
                Stat stat = client.checkExists().forPath(path);
                if (stat == null) {
                    client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, value.getBytes(StandardCharsets.UTF_8));
                    log.info("create path: {}, value: {}", path, value);
                }
            } catch (Exception e) {
                log.error("error creating path: {}", path, e);
            }
            // 加入map
            pathToBean.put(path, bean);
            log.info("add path: {}", path);
        }
        return bean;
    }

    @PostConstruct
    public void createListener() {
        // 创建监听，更新属性值
        // 因为指定了namespace，所以这里直接填/
        CuratorCache cache = CuratorCache.build(client, "/");
        cache.listenable().addListener(new CuratorCacheListener() {
            @Override
            public void event(Type type, ChildData oldData, ChildData data) {
                if (!Type.NODE_CHANGED.equals(type)) {
                    return;
                }
                // 节点修改，找到对应的bean修改属性值
                String path = data.getPath();
                Object bean = pathToBean.get(path);
                // 这里默认字段是String类型的，不然还得序列化反序列化等
                String newValue = new String(data.getData(), StandardCharsets.UTF_8);
                try {
                    // 属性名是path最后那个
                    String[] split = path.split("/");
                    String fieldName = split[split.length - 1];
                    Field field = bean.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(bean, newValue);
                    field.setAccessible(false);
                } catch (Exception e) {
                    log.error("error refresh field. path: {}", path, e);
                }
                log.info("refresh {} ----> {}", path, newValue);
            }
        });
        cache.start();
        log.info("cache started");
    }
}
