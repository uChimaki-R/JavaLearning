package com.pockyr.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestDemo {
    public static void main(String[] args) {
        String phoneNumbers = "136-555-6666";
        if (phoneNumbers.matches("1\\d{2}-\\d{3}-\\d{4}")) {
            System.out.println("Valid phone number");
        } else {
            System.out.println("Invalid phone number");
        }

        String data = """
                 P191 01、Java高级：junit单元测试框架详解 38:33
                 P192 02、Java高级：反射-认识反射、获取类 13:10
                 P193 03、Java高级、反射-获取构造器对象并使用 22:32
                 P194 04、Java高级：反射-获取成员变量和方法对象并使用 28:50
                 P195 05、Java高级：反射的作用、应用场景 14:56
                 P196 06、Java高级：注解、自定义注解、元注解 20:53
                 P197 07、Java高级：注解的应用场景：模拟junit框架 22:57
                 P198 08、Java高级：动态代理设计模式介绍、准备工作、代码实现 25:41
                 P199 09、Java高级：动态代理的应用场景和好处 11:27
                 P200 黄埔班期末考试真题 2:55:24\
                """;
        Pattern pattern = Pattern.compile("\\d{2}:\\d{2}");
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
        String dataReplaced = data.replaceAll("\\d{2}、Java高级", "你猜高不高级?");
        System.out.println(dataReplaced);

        String[] splits = dataReplaced.split("你猜高不高级\\?");
        for (String s : splits) {
            System.out.println(s);
        }
    }
}
