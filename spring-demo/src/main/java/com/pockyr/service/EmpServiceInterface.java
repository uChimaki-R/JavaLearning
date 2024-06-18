package com.pockyr.service;

import com.pockyr.pojo.PageBean;

public interface EmpServiceInterface {
    PageBean page(Integer page, Integer pageSize);
}
