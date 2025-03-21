package shardingJDBC.standard;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-21
 * @Description: 标准模式下的分库分表测试启动类
 * @Version: 1.0
 */
@SpringBootApplication
@MapperScan("shardingJDBC.standard.mapper")
public class StandardApplication {
    public static void main(String[] args) {
        SpringApplication.run(StandardApplication.class, args);
    }
}
