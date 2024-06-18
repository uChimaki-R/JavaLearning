package com.pockyr.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pockyr.mapper.EmpMapper;
import com.pockyr.pojo.Employee;
import com.pockyr.pojo.PageBean;
import com.pockyr.service.EmpServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpService implements EmpServiceInterface {
    @Autowired
    private EmpMapper empMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize, String namePart, Short gender, LocalDate begin, LocalDate end) {
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
        // 执行查询语句
        List<Employee> employees = empMapper.selectByConditions(namePart, gender, begin, end);
        // 将结果包装为Page<>
        Page<Employee> empPage = (Page<Employee>) employees;

        // 使用Page的方法获取信息
        return new PageBean(empPage.getTotal(), empPage.getResult());
    }

    @Override
    public void delete(List<Integer> ids) {
        empMapper.delete(ids);
    }

    @Override
    public void add(Employee employee) {
        // 补充基本信息
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        empMapper.add(employee);
    }
}
