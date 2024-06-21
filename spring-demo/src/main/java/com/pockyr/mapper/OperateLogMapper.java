package com.pockyr.mapper;

import com.pockyr.pojo.OperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperateLogMapper {
    @Insert("insert into operate_log values(#{id}, #{operateUser}, #{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime})")
    void insertOperateLog(OperateLog operateLog);
}
