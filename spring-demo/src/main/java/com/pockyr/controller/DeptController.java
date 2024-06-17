package com.pockyr.controller;

import com.pockyr.pojo.Department;
import com.pockyr.pojo.Result;
import com.pockyr.service.impl.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping("/depts")
    public Result getDepartments() {
        List<Department> departments = deptService.getDepartments();
        return Result.success(departments);
    }
}
