package com.pockyr.HelloSpringBoot.controller;

//import jakarta.servlet.http.HttpServletRequest;

import com.pockyr.HelloSpringBoot.pojo.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class RequestController {
//    @RequestMapping("/params")
//    public String getSomeParams(HttpServletRequest request) {
//        String name = request.getParameter("name");
//        String strAge = request.getParameter("age");
//        // HttpServletRequest获取的参数都是String类型，需要手动转换类型
//        int age = Integer.parseInt(strAge);
//        System.out.println(name + ": " + age);
//        return "OK";
//    }

    @RequestMapping("/params")
    public String getSomeParams(String name, Integer age) {
        System.out.println(name + ": " + age);
        return "OK";
    }

    @RequestMapping("/pojoParams")
    public String getSomePojoParams(User user) {
        // 复杂实体类中的简单实体类对象的数据(假设对象名为address)，请求需要传递参数为address.city="xxx" address.street="xxx"
        System.out.println(user);
        // User{name='Tom', age=20, address=Address{city='广州', street='天河'}}
        return "OK";
    }

    @RequestMapping("/arrayParams")
    public String getSomeArrayParams(String[] hobby) {
        // 对一个请求参数的多个数据传递可以包装成数组
        System.out.println(Arrays.toString(hobby));
        // [Java, game, badminton]
        return "OK";
    }

    @RequestMapping("/listParams")
    public String getSomeListParams(@RequestParam List<String> objs) {
        // 对一个请求参数的多个数据传递可以包装成容器，容器都需要加上@RequestParam注释，否则会报错
        System.out.println(objs);
        return "OK";
    }

    @RequestMapping("/dateParam")
    public String getDateParam(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updateDate) {
        // 接收日期时间类数据，需要使用@DateTimeFormat注解声明数据格式
        System.out.println(updateDate);
        return "OK";
    }

    @RequestMapping("/jsonParams")
    public String getSomeJsonParams(@RequestBody List<User> users) {
        // 传递json格式数据，也需要名称一一对应，参数需要使用@RequestBody注解
        System.out.println(users);
        // [User{name='Tom', age=12, address=Address{city='Beijing', street='ChaoYang'}}, User{name='Mary', age=18, address=Address{city='Guangzhou', street='BaiYun'}}]
        return "OK";
    }

    @RequestMapping("/pathParams/{id}/{name}")
    public String pathParam(@PathVariable int id, @PathVariable String name) {
        // 解析同一类路径的请求
        System.out.println(id + ": " + name);
        return "OK";
    }
}
