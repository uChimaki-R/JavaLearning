package shardingJDBC.standard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import shardingJDBC.standard.po.Course;

/**
* @Author: Ray-C
* @CreateTime: 2025-03-21
* @Description: course逻辑库mapper
* @Version: 1.0
*/
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
}
