package com.pockyr.controller;

import com.pockyr.pojo.PageBean;
import com.pockyr.pojo.Result;
import com.pockyr.service.impl.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpController {
    @Autowired
    private EmpService empService;

    /**
     * 获取指定页数中的Employee数据和数据总数total, 包装为实体类PageBean, 传递到响应信息中
     * @param page 页数
     * @param pageSize 一页的数据量
     * @return 响应信息
     */
    @GetMapping("/emps")
    public Result page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageBean pageBean = empService.page(page, pageSize);
        return Result.success(pageBean);
    }
}
