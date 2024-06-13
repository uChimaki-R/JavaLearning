package com.pockyr;

import com.pockyr.mapper.FactoryMapper;
import com.pockyr.pojo.Factory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HelloSpringMybatisApplicationTests {
    @Autowired
    private FactoryMapper factoryMapper;

    @Test
    void testGetFactories() {
        List<Factory> factories = factoryMapper.getFactories();
        System.out.println(factories);
        /*
        [Factory{JNO='J1', JNAME='三建', JCITY='北京'},
         Factory{JNO='J2', JNAME='一汽', JCITY='长春'},
         Factory{JNO='J3', JNAME='弹簧厂', JCITY='天津'},
         Factory{JNO='J4', JNAME='造船厂', JCITY='天津'},
         Factory{JNO='J5', JNAME='机车厂', JCITY='唐山'},
         Factory{JNO='J6', JNAME='无线电厂', JCITY='常州'},
         Factory{JNO='J7', JNAME='半导体厂', JCITY='南京'}]
         */
    }

}
