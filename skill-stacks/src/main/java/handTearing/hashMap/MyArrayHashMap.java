package handTearing.hashMap;

import java.util.ArrayList;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-02
 * @Description: 手撕 HashMap（最简单的ArrayList实现，与hash没有半毛钱关系）
 * @Version: 1.0
 */
public class MyArrayHashMap<K, V> implements MyHashMapInter<K, V> {
    /**
     * hashmap存储的每个元素是一个键值对
     */
    static class Pair<K, V> {
        public K key;
        public V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // 最简单的实现是使用ArrayList，会自动扩容
    private final ArrayList<Pair<K, V>> pairs = new ArrayList<Pair<K, V>>();

    @Override
    public V put(K key, V value) {
        // put不能直接插入，因为key对应的元素可能存在，此时就不是新增而是修改
        // 因为get方法返回的是旧的值，无法在这里复用，也是编写遍历逻辑
        for (Pair<K, V> pair : pairs) {
            if (pair.key.equals(key)) {
                // 修改原来的值，返回旧的值
                V oldValue = pair.value;
                pair.value = value;
                return oldValue;
            }
        }
        // 遍历结束也没有修改返回，说明是新增，插入新的值
        pairs.add(new Pair<>(key, value));
        return null;
    }

    @Override
    public V get(K key) {
        // 最简单的实现就是遍历列表查找
        for (Pair<K, V> pair : pairs) {
            if (pair.key.equals(key)) {
                return pair.value;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        // 由于ArrayList的remove需要指定下标，所以使用fori遍历
        for (int i = 0; i < pairs.size(); i++) {
            if (pairs.get(i).key.equals(key)) {
                V oldValue = pairs.get(i).value;
                pairs.remove(i);
                return oldValue;
            }
        }
        return null;
    }

    @Override
    public int size() {
        // 数组大小就是键值对个数
        return pairs.size();
    }
}
