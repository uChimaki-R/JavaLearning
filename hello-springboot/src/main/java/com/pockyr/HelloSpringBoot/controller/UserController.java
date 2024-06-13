package com.pockyr.HelloSpringBoot.controller;

import com.pockyr.HelloSpringBoot.pojo.Result;
import com.pockyr.HelloSpringBoot.pojo.User;
import com.pockyr.HelloSpringBoot.service.UserServiceInterface;
import com.pockyr.HelloSpringBoot.service.impl.UserServiceImplA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
// 等价下面两个注解
// @ResponseBody 注解在类上代表类中的方法返回值都会被包装为body中的数据
// @Component
public class UserController {
    @Autowired
    private UserServiceInterface userService;
    @RequestMapping("/getUserData")
    public Result getUserData() {
        List<User> users = userService.getFormattedUsers();
        return Result.success(users);
    }
}
