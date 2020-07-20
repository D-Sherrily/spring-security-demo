package com.you.springsecuritydemo.service;

import com.you.springsecuritydemo.domain.entity.LoginUser;

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
    public LoginUser getLoginUserInfo(String username);
}
