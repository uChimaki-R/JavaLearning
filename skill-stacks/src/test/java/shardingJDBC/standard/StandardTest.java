package shardingJDBC.standard;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import shardingJDBC.standard.mapper.CourseMapper;
import shardingJDBC.standard.po.Course;

import javax.annotation.Resource;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-21
 * @Description: 标准模式下的分库分表查询测试
 * @Version: 1.0
 */
@SpringBootTest
public class StandardTest {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private CourseMapper courseMapper;

    @Test
    public void createTable() {
        jdbcTemplate.execute("create table course\n" +
                "(\n" +
                "    cid     bigint         not null comment '课程id'\n" +
                "        primary key,\n" +
                "    cname   varchar(15) null comment '课程名称',\n" +
                "    c_status tinyint     null comment '课程状态'\n" +
                ")\n" +
                "    comment '用于测试分库分表的课程数据库';\n" +
                "\n");
    }

    @Test
    public void insert() {
        Course course = new Course();
        course.setCname("数学");
        course.setCStatus(1);
        for (int i = 0; i < 10; i++) {
            courseMapper.insert(course);
        }
    }

    @Test
    public void select() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", 1109854822762283008L);
        Course course = courseMapper.selectOne(queryWrapper);
        System.out.println(course);
    }
}