package com.you.springsecuritydemo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.you.springsecuritydemo.domain.pojo.Permission;
import com.you.springsecuritydemo.mapper.PermissionMapper;
import com.you.springsecuritydemo.service.TestService;
import com.you.springsecuritydemo.service.impl.TestServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TestController
 * @Description: 测试用的controller
 * @author: D
 * @create: 2020-06-23 16:34
 **/
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {
    @Resource
    private TestServiceImpl testService;




    @ApiOperation(value = "测试")
    //@PreAuthorize("hasAnyAuthority('sys:user:manager','sys:user:query','sys:user:password')")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public PageInfo<Permission> query(Integer pageNum, Integer pageSize) {
        log.info("pageNum:"+pageNum);
        log.info("pageSize:"+pageSize);


        PageHelper.startPage(pageNum,pageSize);
        List<Permission> permissions = testService.test();
        permissions.forEach(System.out::println);

        PageInfo<Permission> pageInfo = new PageInfo<>(permissions);
        int size = pageInfo.getSize();

        return pageInfo;


    }



}
