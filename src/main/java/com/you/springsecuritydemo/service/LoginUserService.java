package com.you.springsecuritydemo.service;

import com.you.springsecuritydemo.domain.dto.LoginUserDto;
import com.you.springsecuritydemo.domain.pojo.Permission;
import com.you.springsecuritydemo.domain.pojo.User;
import com.you.springsecuritydemo.mapper.PermissionMapper;
import com.you.springsecuritydemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: LoginUserService
 * @Description: 用户登录处理的service  实现了userDetailsService
 * @author: D
 * @create: 2020-06-24 10:11
 **/


public interface LoginUserService {
    /**
     * @Description 根据用户名获取用户权限等信息
     **/
    public LoginUserDto getLoginUserInfo(String username);
}
