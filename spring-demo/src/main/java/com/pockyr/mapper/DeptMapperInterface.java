package com.pockyr.mapper;

import com.pockyr.pojo.Department;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptMapperInterface {
    @Select("select * from dept")
    List<Department> getAll();

    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into dept values(#{id}, #{name}, #{createTime}, #{updateTime})")
    void add(Department department);
}
