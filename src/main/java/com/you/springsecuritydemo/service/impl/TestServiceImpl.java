package com.you.springsecuritydemo.service.impl;

import com.github.pagehelper.PageInfo;
import com.you.springsecuritydemo.domain.pojo.Permission;
import com.you.springsecuritydemo.mapper.PermissionMapper;
import com.you.springsecuritydemo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: TestServiceImpl
 * @Description: test
 * @author: D
 * @create: 2020-07-27 10:35
 **/
@Slf4j
@Service
public class TestServiceImpl implements TestService {
    @Resource
    private PermissionMapper permissionMapper;
    @Override
    public List<Permission> test() {

        List<Permission> permissions = permissionMapper.selectAll();
        log.info("permission :"+permissions.size());

        return permissions;
    }
}
