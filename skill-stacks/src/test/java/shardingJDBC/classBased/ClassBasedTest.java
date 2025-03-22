package shardingJDBC.classBased;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import shardingJDBC.mapper.CourseMapper;
import shardingJDBC.po.Course;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-22
 * @Description: CLASS_BASED分片测试
 * @Version: 1.0
 */
@SpringBootTest
public class ClassBasedTest {
    @Resource
    private CourseMapper courseMapper;

    @Test
    public void select() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .in(Course::getCid, Arrays.asList(1109854822762283008L, 1109854822821003264L))
//                .ge(Course::getCStatus, 0)
//                .le(Course::getCStatus, 3);
                .ge(Course::getCStatus, -5)
                .le(Course::getCStatus, -1);
        List<Course> courses = courseMapper.selectList(queryWrapper);
        System.out.println(courses);
    }

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
}
