package com.pockyr.pojo;

import lombok.*;

//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
@Data // 等价@Getter、@Setter、@ToString、@EqualsAndHashCode
@NoArgsConstructor // 无参构造
@AllArgsConstructor // 非静态成员的所有变量的有参构造
public class Factory {
    private String JNO;
    private String JNAME;
    private String JCITY;
}
