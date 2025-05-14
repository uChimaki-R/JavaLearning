package handTearing.spring.framework.processors;

import handTearing.spring.framework.anno.Component;
import handTearing.spring.framework.inter.BeanPostProcessor;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-05-12
 * @Description: 用于测试bean生命周期逻辑的processor
 * @Version: 1.0
 */
@Component
public class MyProcessor implements BeanPostProcessor {

    @Override
    public Object beforeInitializeBean(Object bean, String beanName) {
        System.out.println("MyProcessor#beforeInitializeBean process bean: " + beanName);
        return bean;
    }

    @Override
    public Object afterInitializeBean(Object bean, String beanName) {
        System.out.println("MyProcessor#afterInitializeBean process bean: " + beanName);
        return bean;
    }
}
