package xxlJob.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-20
 * @Description: xxl-job调用的方法
 * @Version: 1.0
 */
@Slf4j
@Component
public class JobHandler {
    @XxlJob("simple-job")
    public ReturnT<String> simpleJob() {
        log.info("执行xxl-job, 接收参数: {}", XxlJobHelper.getJobParam());
        return ReturnT.SUCCESS;
    }
}
