package com.pockyr.HelloSpringBoot.service.impl;

import com.pockyr.HelloSpringBoot.dao.UserDaoInterface;
import com.pockyr.HelloSpringBoot.dao.impl.UserDaoImplA;
import com.pockyr.HelloSpringBoot.pojo.Address;
import com.pockyr.HelloSpringBoot.pojo.User;
import com.pockyr.HelloSpringBoot.service.UserServiceInterface;

import java.util.List;

public class UserServiceImplA implements UserServiceInterface {
    // 先要获取数据
    UserDaoInterface originDataDao = new UserDaoImplA();
    @Override
    public List<User> getFormattedUsers() {
        List<User> data = originDataDao.getUsers();
        // 对数据进行处理
        // 我这里没什么要处理的，就把address中的字母都变大写吧
        data.forEach(user -> {
            Address address = user.getAddress();
            address.setCity(address.getCity().toUpperCase());
            address.setStreet(address.getStreet().toUpperCase());
            user.setAddress(address);
        });
        return data;
    }
}
