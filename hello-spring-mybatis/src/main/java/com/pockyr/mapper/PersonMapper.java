package com.pockyr.mapper;

import com.pockyr.pojo.Person;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PersonMapper {
    // 查询
    @Select("select * from mybatis_test")
    List<Person> getPersons();

    // 删除
    @Delete("delete from mybatis_test where id = #{id}")
    void deletePerson(int id);  // 可以有返回值int 返回受影响的行数

    // 新增
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into mybatis_test value (#{id}, #{name}, #{age}, #{gender}, #{createTime}, #{updateTime})") // 使用类实例传递时#{}里面的参数名字要和类中的变量名完全一致
    void insertPerson(Person person);
}
