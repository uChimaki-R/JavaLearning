package multiThread;

import java.util.concurrent.TimeUnit;

public class VolatileDemo {
    //    int i = 0;
    volatile int i = 0;
    // 主要两个作用（这里演示了第一个作用）：
    // 1、保证了不同线程对共享数据的可见性，如果其中一个线程对volatile标记的属性进行了修改，其他线程都能够感知到
    // 2、禁止了指令的重排。通过添加内存屏障防止指令重排。
    //    指令重排多线程错误示例：正常赋值顺序：1、分配空间和初始值a=0  2、赋值a=8  3、将对象的属性指向a
    //                              优化为：1、分配空间和初始值a=0  2、将对象的属性指向a  3、赋值a=8
    //                         这在多线程情况下会出问题，如果在优化后的第2步之后某个线程直接拿了这个对象，则a值错误（是0而不是8）

    public void task() {
        System.out.println("线程开始执行");
        while (i == 0) {
            ;
        }
        System.out.println("线程执行结束");
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileDemo volatileDemo = new VolatileDemo();

        Thread thread = new Thread(volatileDemo::task);

        thread.start();

        TimeUnit.SECONDS.sleep(2); // 主线程睡一会再去修改，效果才明显

        volatileDemo.i = 1;
    }
}
