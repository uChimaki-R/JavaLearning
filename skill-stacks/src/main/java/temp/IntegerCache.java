package temp;

public class IntegerCache {
    public static void main(String[] args) {
        // 享元机制，[-128, 127] 之间的Integer对象会缓存，数字存在返回同一个对象，不存在则创建
        Integer a = 100; // 装箱操作
        Integer b = 100;
        System.out.println(a == b); // true 比较内存地址

        Integer c = 300;
        Integer d = 300;
        System.out.println(c == d); // false c d 不在享元机制范围内，获取的是不同的对象
    }
}
