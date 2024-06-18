package com.pockyr.controller;

import com.pockyr.pojo.Department;
import com.pockyr.pojo.Result;
import com.pockyr.service.impl.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/depts") // 如果路径前有统一的部分，可以提取到类注解上，方法中的部分可以省略
public class DeptController {
    @Autowired
    private DeptService deptService;

    /**
     * 获取全部部门信息
     *
     * @return 响应信息
     */
    @GetMapping
    public Result getAll() {
        List<Department> departments = deptService.getAll();
        log.debug("获取全部部门信息, 总数: {}", departments.size());
        return Result.success(departments);
    }

    /**
     * 根据部门id获取部门信息
     *
     * @param id 部门id
     * @return 响应信息
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Department department = deptService.getById(id);
        log.info("查询部门: {}", department);
        return Result.success(department);
    }

    /**
     * 根据部门id删除指定部门信息
     *
     * @param id 部门id
     * @return 响应信息
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id) {
        deptService.deleteById(id);
        log.info("删除部门, 部门id: {}", id);
        return Result.success();
    }

    /**
     * 添加一个新的部门
     *
     * @param department 部门信息(仅在body传递部门的名称)
     * @return 响应信息
     */
    @PostMapping
    public Result add(@RequestBody Department department) {
        deptService.add(department);
        log.info("添加部门: {}", department.getName());
        return Result.success();
    }

    /**
     * 根据部门id更新部门信息(名称)
     *
     * @param department 部门信息(在body中传递需要更新的部门id及其新的部门名称)
     * @return 响应信息
     */
    @PutMapping
    public Result update(@RequestBody Department department) {
        deptService.update(department);
        log.info("更新id为{}的部门名称为: {}", department.getId(), department.getName());
        return Result.success();
    }
}
