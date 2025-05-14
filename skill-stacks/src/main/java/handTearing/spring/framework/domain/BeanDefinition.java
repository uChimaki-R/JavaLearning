package handTearing.spring.framework.domain;

import handTearing.spring.framework.anno.Autowired;
import handTearing.spring.framework.anno.Component;
import handTearing.spring.framework.anno.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-05-06
 * @Description: bean对象定义类，包装bean创建时所需的信息
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
public class BeanDefinition {
    private final String name;
    private final Class<?> beanClass;
    private final Constructor<?> constructor;
    private final List<Field> autowiredFields;
    private final List<Method> postConstructMethods;

    public BeanDefinition(Class<?> clazz) {
        this.beanClass = clazz;
        Component componentAnno = clazz.getDeclaredAnnotation(Component.class);
        name = componentAnno.name().isEmpty() ? clazz.getSimpleName() : componentAnno.name();
        try {
            constructor = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        autowiredFields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Autowired.class))
                .collect(Collectors.toList());
        postConstructMethods = Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PostConstruct.class))
                .collect(Collectors.toList());
    }
}
