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

    // 批量删除
    void deletePersonsByIds(List<Integer> ids);

    // 新增
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into mybatis_test value (#{id}, #{name}, #{age}, #{gender}, #{createTime}, #{updateTime})") // 使用类实例传递时#{}里面的参数名字要和类中的变量名完全一致
    void insertPerson(Person person);

    // 查询
    @Select("select * from mybatis_test where id = #{id}")
    // 数据库中的列名和类中的变量名不同时无法自动匹配的解决办法：
    // 1、起别名
    // @Select("select id, name, age, gender, create_time createTime, update_time updateTime from mybatis_test where id = #{id}")
    // 2、使用注解映射
//    @Results({
//            @Result(column = "create_time", property = "createTime"),
//            @Result(column = "update_time", property = "updateTime")
//    })
    // 3、.properties文件添加配置mybatis.configuration.map-underscore-to-camel-case=true
    Person getPerson(int id);

//    // 条件查询
//    @Select("select * from mybatis_test where name like concat('%', #{nameLike}, '%') and age between #{startAge} and #{endAge} order by update_time")
////    List<Person> getPersons(String nameLike, short startAge, short endAge); // 函数重名过不了测试
//    List<Person> selectPersons(String nameLike, short startAge, short endAge);

    // 条件查询
    // 使用xml文件来绑定sql语句，xml文件放在resources下的同名路径下
    List<Person> selectPersons(String nameLike, Short startAge, Short endAge);

//    // 下面这种如果传递进来的person没有设置好全部的参数的话，方法会传递null，导致数据被覆盖丢失，需要使用动态sql语句解决
//    // 更新
//    @Update("update mybatis_test set name = #{name}, age = #{age}, gender = #{gender}, update_time = #{updateTime} where id = #{id}")
//    // 更新数据记得要更新update_time
//    void updatePerson(Person person);
    void updatePerson(Person person);
}
