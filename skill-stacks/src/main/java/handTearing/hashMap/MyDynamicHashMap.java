package handTearing.hashMap;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-02
 * @Description: 动态扩容的拉链 HashMap
 * @Version: 1.0
 */
public class MyDynamicHashMap<K, V> implements MyHashMapInter<K, V> {
    static class Pair<K, V> {
        public K key;
        public V value;
        public Pair<K, V> next;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 扩容的大小是有说法的，我们每次扩容到原来的2倍，如果我们初始化的大小就是2的倍数的话，后续大小也会是2的倍数<br>
     * 这样有一个好处：可以使用位运算替代模运算，因为：<br>
     * 1. (2的倍数-1)在二进制下尾数全1，如果和(2的倍数-1)相与，得出的结果肯定小于(2的倍数-1)，即结果处于数组合法下标<br>
     * 2. 在[0, (2的倍数-1)]之间的数都有可能获取到，即hash会分散到整个数组下标，不会出现某个下标不会被hash到的问题<br>
     */
    private Pair<K, V>[] pairs = new Pair[16];
    private int size = 0;

    private int getIndex(K key) {
        // 使用&运算代替%运算
        return key.hashCode() & (pairs.length - 1);
    }

    // 记录下扩容次数
    private int expandTimes = 0;

    /**
     * 扩容的核心逻辑，所有size++处都要调用
     */
    private void expandIfNeeded() {
        // 达到3/4就扩容
        if (size < pairs.length * 0.75) {
            return;
        }
        // 获取新数组
        Pair<K, V>[] newPairs = new Pair[pairs.length * 2];
        // 遍历迁移原来的数据
        Pair<K, V> current;
        for (Pair<K, V> head : pairs) {
            if (head == null) {
                continue;
            }
            current = head;
            // 遍历这个链表
            while (current != null) {
                // 获取该键值对的新下标
                int newIndex = current.key.hashCode() & (newPairs.length - 1);
                if (newPairs[newIndex] == null) {
                    // 直接插入
                    // !!!! 注意，插入过去的头节点应该不带任何后续节点 !!!! 所以需要断掉next再插入到新pairs
                    Pair<K, V> next = current.next;
                    current.next = null;
                    newPairs[newIndex] = current;
                    // 接着遍历
                    current = next;
                } else {
                    // 头插法
                    // 先把next保存
                    Pair<K, V> next = current.next;
                    // 连接节点
                    current.next = newPairs[newIndex];
                    // 替换到头节点
                    newPairs[newIndex] = current;
                    // 接着遍历
                    current = next;
                }
            }
        }
        // 迁移结束，修改引用
        pairs = newPairs;
        System.out.printf("第[%d]次扩容 ---> %d\n", ++expandTimes, newPairs.length);
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
            expandIfNeeded();
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
                expandIfNeeded();
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
