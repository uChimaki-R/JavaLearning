package shardingJDBC.po;

import lombok.Data;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-21
 * @Description: 用于测试的课程实体类
 * @Version: 1.0
 */
@Data
public class Course {
    private Long cid;
    private String cname;
    private Integer cStatus;
}
