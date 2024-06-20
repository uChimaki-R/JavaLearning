package com.pockyr.controller;

import com.pockyr.pojo.Employee;
import com.pockyr.pojo.Result;
import com.pockyr.service.EmpServiceInterface;
import com.pockyr.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private EmpServiceInterface empService;

    @PostMapping("/login")
    public Result login(@RequestBody Employee employee) {
        log.info("检测员工登录合法性, username: {}, password: {}", employee.getUsername(), employee.getPassword());
        Employee e = empService.getEmployeeByUsernameAndPassword(employee);
        if (e != null) {
            // 返回令牌
            Map<String, Object> chaim = new HashMap<>();
            chaim.put("id", e.getId());
            chaim.put("name", e.getName());
            chaim.put("username", e.getUsername());
            String token = JWTUtils.generateJWT(chaim);
            log.info("员工登录成功, 生成token: {}", token);
            return Result.success(token);
        }
        log.info("员工username或password错误, 登录失败");
        return Result.error("NOT_LOGIN");
    }
}
