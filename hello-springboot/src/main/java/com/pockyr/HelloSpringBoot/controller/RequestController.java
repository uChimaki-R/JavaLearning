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
        // 复杂实体类中的简单实体类对象的数据(假设对象名为address)，请求需要传递参数为address.city="xxx" address.street="xxx"
        System.out.println(user);
        // User{name='Tom', age=20, address=Address{city='广州', street='天河'}}
        return "OK";
    }
}
