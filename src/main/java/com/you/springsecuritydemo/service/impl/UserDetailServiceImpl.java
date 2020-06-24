package com.you.springsecuritydemo.service.impl;

import com.you.springsecuritydemo.domain.dto.LoginUserDto;
import com.you.springsecuritydemo.service.LoginUserService;
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
        LoginUserDto loginUserInfo = loginUserServiceimpl.getLoginUserInfo(s);
        UserDetails userDetails = User
                .withUsername(loginUserInfo.getUsername())
                .password(loginUserInfo.getPassword())
                .authorities(loginUserInfo.getPermissionsArr()).build();

        return userDetails;
    }
}
