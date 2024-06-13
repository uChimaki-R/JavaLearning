package com.pockyr.HelloSpringBoot.dao;

import com.pockyr.HelloSpringBoot.pojo.User;

import java.util.List;

public interface UserDaoInterface {
    // User Data Access Object
    public List<User> getUsers();
}
