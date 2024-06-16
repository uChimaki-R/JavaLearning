package com.pockyr.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private int id;
    private String name;
    private short age;
    private short gender;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
