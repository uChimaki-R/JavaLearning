package com.pockyr;

import com.pockyr.mapper.FactoryMapper;
import com.pockyr.mapper.PersonMapper;
import com.pockyr.pojo.Factory;
import com.pockyr.pojo.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class HelloSpringMybatisApplicationTests {
    @Autowired
    private FactoryMapper factoryMapper;
    @Autowired
    private PersonMapper personMapper;

    @Test
    void testGetFactories() {
        List<Factory> factories = factoryMapper.getFactories();
        System.out.println(factories);
        /*
        [Factory(JNO=J1, JNAME=三建, JCITY=北京),
         Factory(JNO=J2, JNAME=一汽, JCITY=长春),
         Factory(JNO=J3, JNAME=弹簧厂, JCITY=天津),
         Factory(JNO=J4, JNAME=造船厂, JCITY=天津),
         Factory(JNO=J5, JNAME=机车厂, JCITY=唐山),
         Factory(JNO=J6, JNAME=无线电厂, JCITY=常州),
         Factory(JNO=J7, JNAME=半导体厂, JCITY=南京)]
         */
    }

    @Test
    void testDeleteFactory() {
        factoryMapper.deleteFactory(7);
    }

    @Test
    void testInsertFactory() {
        factoryMapper.insertFactory(new Factory("J7", "测试厂", "广州"));
    }

    @Test
    void testInsertPerson(){
        Person person = new Person();
        person.setName("Ben");
        person.setAge((short)10);
        person.setGender((short)1);
        person.setCreateTime(LocalDateTime.now());
        person.setUpdateTime(LocalDateTime.now());
        personMapper.insertPerson(person);
        // 只有在mapper中使用了 useGeneratedKeys = true 并使用 keyProperty = "id" 指明主键名称才能够获取到主键信息
        System.out.println(person.getId()); // 否则会输出0/null
    }
}
