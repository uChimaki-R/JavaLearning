package handTearing.spring.framework.inter;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-05-12
 * @Description: bean初始化生命周期接口
 * @Version: 1.0
 */
public interface BeanPostProcessor {
    default Object beforeInitializeBean(Object bean, String beanName) {
        return bean;
    }

    default Object afterInitializeBean(Object bean, String beanName) {
        return bean;
    }
}
