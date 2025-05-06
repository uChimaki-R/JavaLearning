package handTearing.hashMap;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-02
 * @Description: 手撕 HashMap，定义接口
 * @Version: 1.0
 */
public interface MyHashMapInter<K, V> {
    /**
     * put插入/更新元素，旧元素存在的话会返回旧元素的值，否则返回null
     */
    V put(K key, V value);

    /**
     * get获取key对应的value，不存在则返回null
     */
    V get(K key);

    /**
     * remove的key对应的值存在则返回该值，否则返回null
     */
    V remove(K key);

    /**
     * size返回当前键值对个数
     */
    int size();
}
