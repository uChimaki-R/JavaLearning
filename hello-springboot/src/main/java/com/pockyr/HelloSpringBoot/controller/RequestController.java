package com.pockyr.HelloSpringBoot.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {
    @RequestMapping("/params")
    public String GetSomeParams(HttpServletRequest request) {
        String name = request.getParameter("name");
        String strAge = request.getParameter("age");
        // HttpServletRequest获取的参数都是String类型，需要手动转换类型
        int age = Integer.parseInt(strAge);
        System.out.println(name + ": " + age);
        return "OK";
    }
}
