package com.pockyr.mapper;

import com.pockyr.pojo.Factory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper // 使用Mapper注解 则运行时会自动生成该接口的实现类对象，无需手动实现
public interface FactoryMapper {
    // 查询请求
    @Select("select * from j")
    List<Factory> getFactories();
}
