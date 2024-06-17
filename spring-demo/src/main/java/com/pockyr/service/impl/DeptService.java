package com.pockyr.service.impl;

import com.pockyr.mapper.DeptMapperInterface;
import com.pockyr.pojo.Department;
import com.pockyr.service.DeptServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService implements DeptServiceInterface {
    @Autowired
    private DeptMapperInterface deptMapper;

    @Override
    public List<Department> getDepartments() {
        return deptMapper.getDepartments();
    }

    @Override
    public void deleteDepartments(Integer id) {
        deptMapper.deleteDepartments(id);
    }
}
