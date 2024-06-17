package com.pockyr.mapper;

import com.pockyr.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptMapperInterface {
    @Select("select * from dept")
    List<Department> getDepartments();
}
