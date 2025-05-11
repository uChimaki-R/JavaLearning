package handTearing.scheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-03
 * @Description: 手写定时任务测试类
 * @Version: 1.0
 */
public class SchedulerTest {
    public static void main(String[] args) {
        MyScheduler myScheduler = new MyScheduler();
        myScheduler.schedule(() -> {
            System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss SSS")) + " " + Thread.currentThread().getName() + " run evey 100ms");
        }, 100);
        myScheduler.schedule(() -> {
            System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss SSS")) + " " + Thread.currentThread().getName() + " run evey 200ms");
        }, 200);
    }
}
