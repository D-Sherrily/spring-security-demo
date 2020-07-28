package com.you.springsecuritydemo.service;

import com.github.pagehelper.PageInfo;
import com.you.springsecuritydemo.domain.pojo.Permission;

import java.util.List;

/**
 * @ClassName: testService
 * @Description: test
 * @author: D
 * @create: 2020-07-27 10:34
 **/

public interface TestService {
    List<Permission> test();
}
