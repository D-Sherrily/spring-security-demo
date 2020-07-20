package com.you.springsecuritydemo.service.impl;

import com.you.springsecuritydemo.domain.entity.LoginUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: UserDetailServiceImpl
 * @Description: UserDetailService 的实现
 * @author: D
 * @create: 2020-06-24 15:51
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private LoginUserServiceImpl loginUserServiceimpl;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LoginUser loginUserInfo = loginUserServiceimpl.getLoginUserInfo(s);
        UserDetails userDetails = User
                .withUsername(loginUserInfo.getUsername())
                .password(loginUserInfo.getPassword())
                .authorities(loginUserInfo.getPermissionsArr()).build();

        return userDetails;
    }
}
