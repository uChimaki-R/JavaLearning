package com.pockyr.HelloSpringBoot.dao.impl;

import com.pockyr.HelloSpringBoot.dao.UserDaoInterface;
import com.pockyr.HelloSpringBoot.pojo.User;
import com.pockyr.HelloSpringBoot.utils.XMLParserUtil;

import java.util.List;
import java.util.Objects;

public class UserDaoImplA implements UserDaoInterface {
    @Override
    public List<User> getUsers() {
        // 获取数据并返回
        String filePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("users.xml")).getFile();
        return XMLParserUtil.parseXML(filePath, User.class);
    }
}
