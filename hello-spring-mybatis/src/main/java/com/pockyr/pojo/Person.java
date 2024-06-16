package com.pockyr.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private Integer id;
    private String name;
    private Short age;
    private Short gender;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
