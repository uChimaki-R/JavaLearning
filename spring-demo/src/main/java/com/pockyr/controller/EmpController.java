package com.pockyr.controller;

import com.pockyr.pojo.Employee;
import com.pockyr.pojo.PageBean;
import com.pockyr.pojo.Result;
import com.pockyr.service.impl.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;

    /**
     * 获取通过条件筛选出来的员工数据中指定页数中的员工数据和数据总数, 包装为实体类PageBean, 传递到响应信息中
     *
     * @param page     页数
     * @param pageSize 一页的数据量
     * @return 响应信息，包含PageBean实体类实例，包装了请求所需的员工数据和数据总数信息
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, Short gender, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        PageBean pageBean = empService.page(page, pageSize, name, gender, begin, end);
        log.info("筛选员工数据, 筛选条件: namePart: {}, gender: {}, entryTimeBegin: {}, entryTimeEnd: {}, 显示页数: {}, 每页数量: {}", name, gender, begin, end, page, pageSize);
        return Result.success(pageBean);
    }

    /**
     * 根据id批量删除员工数据
     *
     * @param ids id列表
     * @return 响应信息
     */
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        empService.delete(ids);
        log.info("删除员工数据, 被删除的id: {}", ids);
        return Result.success();
    }

    /**
     * 添加员工
     *
     * @param employee 包装了body中的员工数据的实体类实例
     * @return 响应信息
     */
    @PostMapping
    public Result add(@RequestBody Employee employee) {
        empService.add(employee);
        log.info("添加员工: {}", employee);
        return Result.success();
    }
}
