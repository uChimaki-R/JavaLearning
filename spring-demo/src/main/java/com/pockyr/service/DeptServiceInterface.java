package com.pockyr.service;

import com.pockyr.pojo.Department;

import java.util.List;

public interface DeptServiceInterface {
    List<Department> getAll();

    void deleteById(Integer id);

    void add(Department department);

    Department getById(Integer id);

    void update(Department department);
}
