package com.pockyr.mapper;

import com.pockyr.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmpMapperInterface {
    @Select("select count(*) from emp;")
    Long count();

    @Select("select * from emp limit #{start}, #{pageSize}")
    List<Employee> page(Integer start, Integer pageSize);
}
