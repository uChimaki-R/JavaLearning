package com.pockyr.mapper;

import com.pockyr.pojo.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {
//    @Select("select count(*) from emp;")
//    Long count();
//
//    @Select("select * from emp limit #{start}, #{pageSize}")
//    List<Employee> page(Integer start, Integer pageSize);

    // 使用了PageHelper插件后只用使用普通的查询语句就行了，无需使用count和limit的sql语句
//    @Select("select * from emp") // 复杂的查询使用xml文件书写
    List<Employee> selectByConditions(String namePart, Short gender, LocalDate begin, LocalDate end);

    void delete(List<Integer> ids);

    @Insert("insert into emp(username, name, gender, image, job, entry_date, dept_id, create_time, update_time) values " +
            "(#{username}, #{name}, #{gender}, #{image}, #{job}, #{entrydate}, #{deptId}, #{createTime}, #{updateTime})")
    void add(Employee employee);

    @Select("select * from emp where id = #{id}")
    Employee getEmployeeById(Integer id);
}
