package com.pockyr.service;

import com.pockyr.pojo.Employee;
import com.pockyr.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

public interface EmpServiceInterface {
    PageBean page(Integer page, Integer pageSize, String namePart, Short gender, LocalDate begin, LocalDate end);

    void delete(List<Integer> ids);

    void add(Employee employee);

    Employee getEmployeeById(Integer id);

    void updateEmployeeById(Employee employee);

    Employee getEmployeeByUsernameAndPassword(Employee employee);
}
