package handTearing.hashMap;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-02
 * @Description: 相对 MyArrayHashMap 加入了hash算法，使用拉链法处理哈希冲突问题，没有动态扩容
 * @Version: 1.0
 */
public class MyFixedHashMap<K, V> implements MyHashMapInter<K, V> {
    /**
     * hashmap存储的每个元素是一个键值对，由于使用拉链法，需要有 next 指向下一个键值对
     */
    static class Pair<K, V> {
        public K key;
        public V value;
        public Pair<K, V> next;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final Pair<K, V>[] pairs = new Pair[10];
    // 由于是固定数组，size不能用数组的length，而需要自己统计，添加时++，删除时--
    private int size = 0;

    private int getIndex(K key) {
        return key.hashCode() % pairs.length;
    }

    @Override
    public V put(K key, V value) {
        // 获取key对应的hash下标，遍历链表查看是否存在该键值对，不存在则插入到尾部并返回null，否则修改值并返回原来的值
        int index = getIndex(key);
        Pair<K, V> head = pairs[index];
        if (head == null) {
            // 没有存过，直接存入并返回null
            head = new Pair<>(key, value);
            pairs[index] = head;
            size++;
            return null;
        }
        // 遍历这个链表，查找是否存在key的键值对
        // （可以直接用head遍历，毕竟head也只是一个引用，这里为了好理解改名为current）
        Pair<K, V> current = head;
        // 尾插法 while true
        while (true) {
            if (current.key.equals(key)) {
                // 原先存在，修改值，返回旧值
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            if (current.next == null) {
                // 遍历到尾部，插入到尾部（因为没有前驱节点，所以不能使用(current == null)的判断）
                current.next = new Pair<>(key, value);
                size++;
                return null;
            }
            // 指针后移
            current = current.next;
        }
    }

    @Override
    public V get(K key) {
        int index = getIndex(key);
        // 拉链法需要遍历取值
        Pair<K, V> current = pairs[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int index = getIndex(key);
        // 遍历去删除该节点，需要前驱节点
        // 判空，为了获取前驱节点
        if (pairs[index] == null) {
            return null;
        }
        Pair<K, V> pre = pairs[index];
        // 前驱节点先判断（因为后面的while中判断的都是current）
        if (pre.key.equals(key)) {
            // 头节点就是要删除的节点，直接把next作为头节点即可
            V oldValue = pre.value;
            pairs[index] = pre.next;
            size--;
            return oldValue;
        }
        // 前面判断了pre不为null，可以next
        Pair<K, V> current = pre.next;
        while (current != null) {
            if (current.key.equals(key)) {
                // 需要删除该节点，返回旧的值
                V oldValue = current.value;
                pre.next = current.next;
                size--;
                return oldValue;
            }
            // 遍历
            pre = pre.next;
            current = current.next;
        }
        // 遍历到最后也没有，说明没有这个key的键值对
        return null;
    }

    @Override
    public int size() {
        return size;
    }
}
