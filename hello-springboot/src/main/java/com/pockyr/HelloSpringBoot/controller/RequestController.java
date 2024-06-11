package com.pockyr.HelloSpringBoot.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {
    @RequestMapping("/params")
    public String GetSomeParams(String name, Integer age) {
        System.out.println(name + ": " + age);
        return "OK";
    }
}
