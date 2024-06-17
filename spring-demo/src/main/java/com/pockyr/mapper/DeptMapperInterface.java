package com.pockyr.mapper;

import com.pockyr.pojo.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapperInterface {
    @Select("select * from dept")
    List<Department> getAll();

    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into dept values(#{id}, #{name}, #{createTime}, #{updateTime})")
    void add(Department department);

    @Select("select * from dept where id = #{id}")
    Department getById(Integer id);

    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    void update(Department department);
}
