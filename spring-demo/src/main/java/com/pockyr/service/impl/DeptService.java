package com.pockyr.service.impl;

import com.pockyr.mapper.DeptMapper;
import com.pockyr.pojo.Department;
import com.pockyr.service.DeptServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptService implements DeptServiceInterface {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Department> getAll() {
        return deptMapper.getAll();
    }

    @Override
    public void deleteById(Integer id) {
        deptMapper.deleteById(id);
    }

    @Override
    public void add(Department department) {
        // 补充信息
        department.setCreateTime(LocalDateTime.now());
        department.setUpdateTime(LocalDateTime.now());
        deptMapper.add(department);
    }

    @Override
    public Department getById(Integer id) {
        return deptMapper.getById(id);
    }

    @Override
    public void update(Department department) {
        // 设置修改时间
        department.setUpdateTime(LocalDateTime.now());
        deptMapper.update(department);
    }
}
