package com.pockyr.mapper;

import com.pockyr.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmpMapperInterface {
//    @Select("select count(*) from emp;")
//    Long count();
//
//    @Select("select * from emp limit #{start}, #{pageSize}")
//    List<Employee> page(Integer start, Integer pageSize);

    // 使用了PageHelper插件后只用使用普通的查询语句就行了，无需使用count和limit的sql语句
    @Select("select * from emp")
    List<Employee> selectAll();
}
