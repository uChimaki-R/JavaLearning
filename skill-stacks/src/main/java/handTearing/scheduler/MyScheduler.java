package handTearing.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-03
 * @Description: 手写定时任务
 * @Version: 1.0
 */
public class MyScheduler {
    /**
     * 具体执行任务的线程池
     */
    private final ExecutorService executor = Executors.newFixedThreadPool(5);


    /**
     * 任务队列
     */
    // 因为任务还要保存开始时间和间隔属性，所以包装为类，实现比较接口用于排序，java默认小顶堆
    private final PriorityBlockingQueue<ScheduleTask> scheduleTasks = new PriorityBlockingQueue<>();

    /**
     * 触发器，在指定时间将任务发送给线程池执行
     */
    private final Trigger trigger = new Trigger();

    /**
     * 提交任务command，每隔delay执行一遍<br>
     * <br>
     * 最容易想到的是直接来一个command就执行一个任务，sleep后执行，再用while包围就可以了<br>
     * 但是这样子线程大多数时间都在阻塞了，很容易打满，而且最多就只能执行线程池大小数量的定时任务，再来任务直接无法分配<br>
     * 所以实际上实现思路是：使用触发器管理这些任务，任务按startTime小到大排序（startTime=cur+delay）<br>
     * 触发器每次取出最小startTime的任务，执行，然后再次创建这个任务放回到任务列表中（同样startTime=cur+delay）<br>
     * <br>
     * 如果使用数组存储任务，每次取任务的时候都要排序一次（因为可能加入了新任务），时间复杂度非常高，而且排序的时候线程不安全，所以应该使用阻塞的优先队列来实现<br>
     * <br>
     * 考虑一种情况，如果触发器正在等待最近的一次任务执行，在这个过程中外部提交了一个比该任务要早执行的任务，光是上述的结构触发器无法做到中断当前等待然后重新执行获取任务的逻辑<br>
     * 所以需要一个类似等待唤醒的机制，在这里使用的是 {@link LockSupport#park()} 和 {@link LockSupport#unpark(Thread)}} 方法（可以不用创建锁对象和使用synchronize）<br>
     * 其中还有一个关于虚假唤醒的坑，可以注意一下
     */
    public void schedule(Runnable command, long delay) {
        // 将交给触发器，让他处理
        ScheduleTask task = new ScheduleTask();
        task.setTask(command);
        task.setDelay(delay);
        task.setStartTime(System.currentTimeMillis() + delay);
        scheduleTasks.offer(task);
        // 唤醒阻塞
        trigger.unPark();
    }

    class Trigger {
        Thread thread = new Thread(() -> {
            while (true) {
                // 不断从任务队列中获取任务
                // 由于上述的需要等待唤醒机制，不能使用take阻塞，而是手动阻塞
                while (scheduleTasks.isEmpty()) {
                    // 由于存在虚假唤醒，所有等待的判断条件都不应该是if而是while，否则跳出后条件判断也不一定不成立，导致后面空指针
                    LockSupport.park();
                }
                // 先peek看下任务是否能执行，如果用poll的话else语句中要把这个任务offer回去，增加操作
                ScheduleTask task = scheduleTasks.peek();
                if (task.getStartTime() < System.currentTimeMillis()) {
                    // 超过了开始时间，需要执行该任务了
                    // 取出任务
                    task = scheduleTasks.poll();
                    // 只有一个trigger在取任务，所以肯定不是null，只是这个task可能不是if外面那个task，而是新增的更早执行的task
                    assert task != null;
                    executor.execute(task.getTask());
                    // 获取下一次执行的时间，重新放回队列
                    ScheduleTask nextTask = new ScheduleTask();
                    // delay后再次执行
                    nextTask.setStartTime(System.currentTimeMillis() + task.getDelay());
                    nextTask.setTask(task.getTask());
                    nextTask.setDelay(task.getDelay());
                    scheduleTasks.offer(nextTask);
                } else {
                    // 说明最早的需要执行的任务都没到执行时间，直接等待这段时间
                    // 但是不能sleep，那个只能中断，使用LockSupport等待，可以被唤醒
                    LockSupport.parkUntil(task.getStartTime());
                }
            }
        });

        // 开启触发器
        {
            thread.start();
        }

        /**
         * 向外提供一个触发唤醒的接口
         */
        public void unPark() {
            LockSupport.unpark(thread);
        }
    }
}
