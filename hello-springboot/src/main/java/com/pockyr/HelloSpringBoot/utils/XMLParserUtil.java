package com.pockyr.HelloSpringBoot.utils;


import com.pockyr.HelloSpringBoot.pojo.Address;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class XMLParserUtil {
    public static <T> List<T> parseXML(String filePath, Class<T> dataClass) {
        // 根据类类型包装XML中的数据为该类型的列表并返回
        List<T> list = new ArrayList<T>();
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(filePath));
            Element root = document.getRootElement();
            List<Element> elements = root.elements();
            for (Element element : elements) {
                String name = element.element("name").getText();
                int age = Integer.parseInt(element.element("age").getText());
                Element addressElement = element.element("address");
                Address address = new Address(addressElement.element("city").getText(), addressElement.element("street").getText());
                // 找到该类使用这些参数构造的构造器
                Constructor<T> constructor = dataClass.getDeclaredConstructor(String.class, int.class, Address.class);
                constructor.setAccessible(true);
                T instance = constructor.newInstance(name, age, address);
                list.add(instance);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}