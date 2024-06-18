package com.pockyr.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pockyr.mapper.EmpMapperInterface;
import com.pockyr.pojo.Employee;
import com.pockyr.pojo.PageBean;
import com.pockyr.service.EmpServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpService implements EmpServiceInterface {
    @Autowired
    private EmpMapperInterface empMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize) {
        // 手动分页
//        // 先获取总数
//        Long count = empMapper.count();
//
//        // 再根据limit获取指定数据
//        Integer start = (page - 1) * pageSize;
//        List<Employee> employees = empMapper.page(start, pageSize);

        // 使用插件分页
        // 设置分页参数
        PageHelper.startPage(page, pageSize);
        // 执行查询语句, 该语句会被PageHelper拦截并替换为手动分页相同的语句
        List<Employee> employees = empMapper.selectAll();
        Page<Employee> empPage = (Page<Employee>) employees; // 转换为Page<>获取相关信息

        // 将总数和页面的数据组装返回
        return new PageBean(empPage.getTotal(), empPage.getResult());
    }
}
