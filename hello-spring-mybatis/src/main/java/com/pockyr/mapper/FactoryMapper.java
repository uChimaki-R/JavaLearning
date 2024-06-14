package com.pockyr.mapper;

import com.pockyr.pojo.Factory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper // 使用Mapper注解 则运行时会自动生成该接口的实现类对象，无需手动实现
public interface FactoryMapper {
    // 查询
    @Select("select * from j")
    List<Factory> getFactories();

    // 删除
    @Delete("delete from j where JNO = concat('J', #{jno})")
    // 使用#{}中可以填写参数，对应方法中的参数
    // #是先在编译时使用一个占位符?占位，并将数据填入
    // 有两个好处：性能好(只有参数不同的sql语句只用编译一次，后续只需要使用之前的缓存就可以)
    //           避免了sql注入，因为其实还可以使用${}，但是$是直接将内容写入sql语句，所以传递的参数中会被注入sql语句从而被入侵
    void deleteFactory(int jno);  // 可以有返回值int 返回受影响的行数
}
