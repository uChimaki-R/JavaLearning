package handTearing.spring.framework;

import handTearing.spring.framework.anno.Component;
import handTearing.spring.framework.domain.BeanDefinition;
import handTearing.spring.framework.inter.BeanPostProcessor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-05-06
 * @Description: 手写 spring上下文对象
 * @Version: 1.0
 */
public class ApplicationContext {
    /**
     * 扫描的包路径
     */
    private final String basePackage;

    /**
     * IOC容器
     */
    private final Map<String, Object> ioc = new HashMap<>();

    /**
     * 存储尚未依赖注入完成的bean对象的IOC容器（二级缓存）
     */
    private final Map<String, Object> loadingIoc = new HashMap<>();

    /**
     * bean名称到BeanDefinition的映射（bean统一由BeanDefinition创建）
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    /**
     * BeanPostProcessor对象列表
     */
    private final List<BeanPostProcessor> processors = new ArrayList<>();

    public ApplicationContext(String basePackage) throws Exception {
        this.basePackage = basePackage;
        initContext();
    }

    /**
     * 根据bean名称获取bean对象
     * 
     * @param beanName bean名称
     * @return 对应的bean对象
     */
    public Object getBean(String beanName) {
        // ioc容器存在则直接返回
        Object bean = ioc.get(beanName);
        if (bean != null) {
            return bean;
        }
        // 存在对应的BeanDefinition则创建bean对象并返回
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition != null) {
            return createBean(beanDefinition);
        }
        // 不存在则返回null
        return null;
    }

    /**
     * 根据bean类型获取bean对象
     * 
     * @param clazz bean类型
     * @return 对应的bean对象
     */
    public <T> T getBean(Class<T> clazz) {
        // 遍历BeanDefinitionMap，找到对应的BeanDefinition获取beanName，调用getBean(String)方法获取bean对象
        return beanDefinitionMap.values().stream()
                .filter(beanDefinition -> clazz.isAssignableFrom(beanDefinition.getBeanClass()))
                .map(beanDefinition -> (T) getBean(beanDefinition.getName()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 根据bean类型获取bean对象列表
     * 
     * @param clazz bean类型
     * @return 对应的bean对象列表
     */
    public <T> List<T> getBeans(Class<T> clazz) {
        return beanDefinitionMap.values().stream()
                .filter(beanDefinition -> clazz.isAssignableFrom(beanDefinition.getBeanClass()))
                .map(beanDefinition -> (T) getBean(beanDefinition.getName()))
                .collect(Collectors.toList());
    }

    /**
     * 初始化上下文对象
     */
    private void initContext() throws Exception {
        // 由于依赖注入和BeanPostProcessor的存在，需要先构造出所有的BeanDefinition对象，然后再创建bean对象
        // 构建BeanDefinition列表
        List<BeanDefinition> beanDefinitions = scanPackage(basePackage).stream()
                .filter(this::canCreate)
                .map(this::toBeanDefinition)
                .collect(Collectors.toList());
        // 存入map属性
        beanDefinitions.forEach(bd -> beanDefinitionMap.put(bd.getName(), bd));
        // 提前创建BeanPostProcessor用于bean生命周期
        beanDefinitions.stream()
                .filter(bd -> BeanPostProcessor.class.isAssignableFrom(bd.getBeanClass()))
                .map(this::createBean)
                .map(b -> (BeanPostProcessor) b)
                .forEach(processors::add);
        // 创建bean对象
        beanDefinitions.forEach(this::createBean);
    }

    /**
     * 创建bean对象，如果bean对象存在于IOC容器中，则直接返回；否则调用{@link #doCreateBean(BeanDefinition)}方法创建bean对象，并将其放入IOC容器中
     * 
     * @param beanDefinition bean定义对象
     * @return 对应的bean对象
     */
    private Object createBean(BeanDefinition beanDefinition) {
        String beanName = beanDefinition.getName();
        // 一级缓存
        if (ioc.containsKey(beanName)) {
            return ioc.get(beanName);
        }
        // 二级缓存
        if (loadingIoc.containsKey(beanName)) {
            return loadingIoc.get(beanName);
        }
        return doCreateBean(beanDefinition);
    }

    /**
     * 真正创建bean对象的方法
     * 
     * @param beanDefinition bean定义对象
     * @return 创建的bean对象
     */
    private Object doCreateBean(BeanDefinition beanDefinition) {
        // 实例化
        Object bean = instantiateBean(beanDefinition);
        // 存入二级缓存中
        loadingIoc.put(beanDefinition.getName(), bean);
        // 依赖注入
        autowiredBeanFields(bean, beanDefinition);
        // 初始化
        bean = initializingBean(bean, beanDefinition);
        // 从二级缓存中移除
        loadingIoc.remove(beanDefinition.getName());
        // 存入IOC容器中
        ioc.put(beanDefinition.getName(), bean);
        return bean;
    }

    /**
     * 调用bean对象的无参构造方法实例化bean对象
     * 
     * @param beanDefinition bean定义对象
     * @return 实例化的bean对象
     */
    private Object instantiateBean(BeanDefinition beanDefinition) {
        Constructor<?> constructor = beanDefinition.getConstructor();
        try {
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对bean对象进行依赖注入
     * 
     * @param bean           bean对象
     * @param beanDefinition bean定义对象
     */
    private void autowiredBeanFields(Object bean, BeanDefinition beanDefinition) {
        beanDefinition.getAutowiredFields().forEach(field -> {
            field.setAccessible(true);
            try {
                // 根据字段的类型进行依赖注入
                field.set(bean, getBean(field.getType()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * bean对象初始化操作，包括前置操作BeanPostProcessor、PostConstruct方法和后置操作BeanPostProcessor
     * 
     * @param bean bean对象
     * @return 初始化完成后的bean对象
     */
    private Object initializingBean(Object bean, BeanDefinition beanDefinition) {
        // 遍历所有BeanPostProcessor，对bean对象调用beforeInitializeBean方法
        for (BeanPostProcessor processor : processors) {
            bean = processor.beforeInitializeBean(bean, beanDefinition.getName());
        }
        // 执行PostConstruct注解的方法
        for (Method method : beanDefinition.getPostConstructMethods()) {
            try {
                method.invoke(bean);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        // 遍历所有BeanPostProcessor，对bean对象调用afterInitializeBean方法
        for (BeanPostProcessor processor : processors) {
            bean = processor.afterInitializeBean(bean, beanDefinition.getName());
        }
        return bean;
    }

    /**
     * 作用于{@link #initContext()}方法中，用于指定{@link #basePackage}路径下哪些类需要加载为bean对象
     *
     * @param clazz {@link #basePackage}路径下的某个类
     * @return 该类是否应该被上下文加载
     */
    protected boolean canCreate(Class<?> clazz) {
        return clazz.isAnnotationPresent(Component.class);
    }

    /**
     * 作用于{@link #initContext()}方法中，用于将{@link #basePackage}路径下的某个类构造成{@link BeanDefinition}对象
     *
     * @param clazz {@link #basePackage}路径下的某个类
     * @return 该类构造的{@link BeanDefinition}对象
     */
    protected BeanDefinition toBeanDefinition(Class<?> clazz) {
        return new BeanDefinition(clazz);
    }

    /**
     * 工具方法，扫描 packageName 路径下的所有类
     *
     * @param packageName 类所在路径
     * @return 该路径下的所有类对象组成的列表
     */
    private List<Class<?>> scanPackage(String packageName) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        // packageName 形如 a.b.c 需要转化为 a/b/c 的文件路径形式
        URL resource = this.getClass().getClassLoader().getResource(packageName.replace(".", File.separator));
        // 使用文件形式遍历
        // walkFileTree 观察者模式
        Files.walkFileTree(Paths.get(resource.toURI()), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String filePath = file.toAbsolutePath().toString();
                if (!filePath.endsWith(".class")) {
                    // 只处理class文件，继续遍历
                    return FileVisitResult.CONTINUE;
                }
                // System.out.println(file.toAbsolutePath());
                // D:\Code\JavaCode\JavaLearning\skill-stacks\target\classes\handTearing\spring\framework\anno\Component.class
                // 需要从上面的路径中获取类的全类名 handTearing.spring.framework.anno.Component
                String replaceStr = filePath.replace(File.separator, ".");
                int packageIndex = replaceStr.indexOf(packageName);
                String className = replaceStr.substring(packageIndex, replaceStr.length() - ".class".length());
                // System.out.println(className);
                try {
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                // 继续遍历
                return FileVisitResult.CONTINUE;
            }
        });
        return classes;
    }
}
