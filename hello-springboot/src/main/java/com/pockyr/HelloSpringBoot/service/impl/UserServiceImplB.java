package com.pockyr.HelloSpringBoot.service.impl;

import com.pockyr.HelloSpringBoot.dao.UserDaoInterface;
import com.pockyr.HelloSpringBoot.pojo.Address;
import com.pockyr.HelloSpringBoot.pojo.User;
import com.pockyr.HelloSpringBoot.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImplB implements UserServiceInterface {
    @Autowired
    private UserDaoInterface originDataDao;
    @Override
    public List<User> getFormattedUsers() {
        List<User> data = originDataDao.getUsers();
        data.forEach(user -> {
            Address address = user.getAddress();
            // 转小写用于对照
            address.setCity(address.getCity().toLowerCase());
            address.setStreet(address.getStreet().toLowerCase());
            user.setAddress(address);
        });
        return data;
    }
}
