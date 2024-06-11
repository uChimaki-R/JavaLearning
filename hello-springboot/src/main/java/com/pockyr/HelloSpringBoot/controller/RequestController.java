package com.pockyr.HelloSpringBoot.controller;

//import jakarta.servlet.http.HttpServletRequest;

import com.pockyr.HelloSpringBoot.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {
//    @RequestMapping("/params")
//    public String GetSomeParams(HttpServletRequest request) {
//        String name = request.getParameter("name");
//        String strAge = request.getParameter("age");
//        // HttpServletRequest获取的参数都是String类型，需要手动转换类型
//        int age = Integer.parseInt(strAge);
//        System.out.println(name + ": " + age);
//        return "OK";
//    }

    @RequestMapping("/params")
    public String GetSomeParams(String name, Integer age) {
        System.out.println(name + ": " + age);
        return "OK";
    }

    @RequestMapping("/pojoParams")
    public String GetSomePojoParams(User user) {
        System.out.println(user);
        return "OK";
    }
}
