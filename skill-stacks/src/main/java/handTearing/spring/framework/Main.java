package handTearing.spring.framework;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-05-06
 * @Description: 测试
 * @Version: 1.0
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ApplicationContext("handTearing.spring.framework");
        context.getBean("Cat");
    }
}
