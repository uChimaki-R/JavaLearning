package com.pockyr.HelloSpringBoot.service;

import com.pockyr.HelloSpringBoot.pojo.User;

import java.util.List;

public interface UserServiceInterface {
    public List<User> getFormattedUsers();
}
