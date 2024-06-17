package com.pockyr.controller;

import com.pockyr.pojo.Department;
import com.pockyr.pojo.Result;
import com.pockyr.service.impl.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    /**
     * 获取全部部门信息
     * @return 响应信息
     */
    @GetMapping("/depts")
    public Result getDepartments() {
        List<Department> departments = deptService.getAll();
        return Result.success(departments);
    }

    /**
     * 根据部门id删除指定部门信息
     * @param id 部门id
     * @return 响应信息
     */
    @DeleteMapping("/depts/{id}")
    public Result deleteDepartments(@PathVariable Integer id) {
        deptService.deleteById(id);
        return Result.success();
    }
}
