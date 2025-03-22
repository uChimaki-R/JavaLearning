package shardingJDBC;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import shardingJDBC.mapper.CourseMapper;
import shardingJDBC.po.Course;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-22
 * @Description: 综合测试
 * @Version: 1.0
 */
@SpringBootTest
public class DemoTest {
    @Resource
    private CourseMapper courseMapper;

    @Test
    public void hintTest() {
        HintManager hintManager = HintManager.getInstance();
        // 指定从course_1获取
        hintManager.addTableShardingValue("course", "1");
        List<Course> allCourse = courseMapper.selectList(null);
        System.out.println(allCourse);
        // 基于ThreadLocal，需要close释放内存，也可以用try-resource包围自动close
        hintManager.close();
    }

    @Test
    public void encryptTest() {
        HintManager hintManager = HintManager.getInstance();
        // 指定从course_1获取
        hintManager.addTableShardingValue("course", "1");

        // 插入测试
        Course course = new Course();
        // 明文插入
        course.setCname("origin course name");
        course.setCStatus(2);
        courseMapper.insert(course);

        // cid不会回填，cname也不会变成加密后的内容
        System.out.println("cid: " + course.getCid()); // null
        System.out.println("cname: " + course.getCname()); // origin course name

        // 查询测试
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        // 明文查询
        queryWrapper.lambda()
                .eq(Course::getCname, "origin course name");
        Course c = courseMapper.selectOne(queryWrapper);
        System.out.println(c);

        // 基于ThreadLocal，需要close释放内存，也可以用try-resource包围自动close
        hintManager.close();
    }

    @Test
    public void readWriteTest() {
        // 插入测试
        Course course = new Course();
        course.setCname("数学");
        course.setCStatus(2);
        courseMapper.insert(course);
        // 插入了m0表

        Long cid = course.getCid();

        // 查询测试
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Course::getCid, cid);
        Course c = courseMapper.selectOne(queryWrapper);
        System.out.println(c);
        // null，因为没有做读表和写表的数据同步，所以在m1表查不到插入到m0表的数据
    }
}
