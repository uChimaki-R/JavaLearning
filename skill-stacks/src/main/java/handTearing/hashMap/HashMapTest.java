package handTearing.hashMap;

import org.junit.jupiter.api.Test;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-02
 * @Description: 测试类
 * @Version: 1.0
 */
public class HashMapTest {
    public MyHashMapInter<String, Integer> myHashMap;

    public void test() {
        int total = 10000;
        for (int i = 0; i < total; i++) {
            myHashMap.put(String.valueOf(i), i);
        }
        // test size
        assert myHashMap.size() == total;
        // test get
        assert myHashMap.get("12") == 12;
        // test remove
        assert myHashMap.remove("12") == 12;
        assert myHashMap.get("12") == null;
        // test put
        Integer oldValue = myHashMap.put("13", 133);
        assert oldValue == 13;
        assert myHashMap.get("13") == 133;
    }

    @Test
    public void testArrayHashMap() {
        myHashMap = new MyArrayHashMap<>();
        test();
    }

    @Test
    public void testFixedHashMap() {
        // 虽然是fixed，但是用了拉链法，所以不扩容也能跑，但是数据量大的时候会退化到O(N)（hash冲突严重，链表过长）
        myHashMap = new MyFixedHashMap<>();
        test();
        // 10万数据下跑了3秒，在容量为10的数组下，最好也是一个链表1万个数据
        // 所以需要实现动态扩容
    }

    @Test
    public void testDynamicHashMap() {
        // 动态扩容实际上就是在fixed基础上加上扩容逻辑，重点是链表的迁移逻辑（遍历头插法）
        myHashMap = new MyDynamicHashMap<>();
        // 10万数据下跑了27毫秒，扩容了14次：第[14]次扩容 ---> 262144
        test();
        // 为了使数据更加分散，需要用到红黑树，比较难，这里没有实现
    }
}
