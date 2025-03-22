package shardingJDBC.classBased;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-22
 * @Description: CLASS_BASED分片测试
 * @Version: 1.0
 */
@SpringBootApplication
@MapperScan("shardingJDBC.mapper")
public class ClassBasedApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClassBasedApplication.class, args);
    }
}
