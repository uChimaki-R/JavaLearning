package handTearing.spring.framework.po;

import handTearing.spring.framework.anno.Autowired;
import handTearing.spring.framework.anno.Component;
import handTearing.spring.framework.anno.PostConstruct;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-05-11
 * @Description: Dog类，用于测试
 * @Version: 1.0
 */
@Component
public class Dog {
    @Autowired
    private Cat cat;

    @Autowired
    private Dog self;

    @PostConstruct
    public void init() {
        System.out.println("Dog: " + this + " init with cat: " + cat + " with self: " + self);
    }
}
