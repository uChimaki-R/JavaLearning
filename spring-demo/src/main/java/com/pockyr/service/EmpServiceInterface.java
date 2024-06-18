package com.pockyr.service;

import com.pockyr.pojo.PageBean;

import java.time.LocalDate;

public interface EmpServiceInterface {
    PageBean page(Integer page, Integer pageSize, String namePart, Short gender, LocalDate begin, LocalDate end);
}
