package com.pockyr.service.impl;

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
        // 先获取总数
        Long count = empMapper.count();

        // 再根据limit获取指定数据
        Integer start = (page - 1) * pageSize;
        List<Employee> employees = empMapper.page(start, pageSize);

        // 将总数和页面的数据组装返回
        return new PageBean(count, employees);
    }
}
