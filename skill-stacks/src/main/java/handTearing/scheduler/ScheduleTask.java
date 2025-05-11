package handTearing.scheduler;

import lombok.Data;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-04
 * @Description: 定时任务类
 * @Version: 1.0
 */
@Data
public class ScheduleTask implements Comparable<ScheduleTask> {
    private Runnable task;
    private Long startTime;
    private Long delay;

    /**
     * 需要根据执行时间排序
     */
    @Override
    public int compareTo(ScheduleTask o) {
        return this.startTime.compareTo(o.startTime);
    }
}
