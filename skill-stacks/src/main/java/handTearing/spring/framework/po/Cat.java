package handTearing.spring.framework.po;

import handTearing.spring.framework.anno.Autowired;
import handTearing.spring.framework.anno.Component;
import handTearing.spring.framework.anno.PostConstruct;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-05-11
 * @Description: Cat类，用于测试
 * @Version: 1.0
 */
@Component
public class Cat {
    @Autowired
    private Dog dog;

    @PostConstruct
    public void init() {
        System.out.println("Cat : " + this + " init with dog: " + dog);
    }
}
