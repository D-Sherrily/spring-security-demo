package com.you.springsecuritydemo.service.impl;

import com.you.springsecuritydemo.domain.entity.LoginUser;
import com.you.springsecuritydemo.domain.pojo.Permission;
import com.you.springsecuritydemo.domain.pojo.User;
import com.you.springsecuritydemo.mapper.PermissionMapper;
import com.you.springsecuritydemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: UserDetailServiceImpl
 * @Description: UserDetailService 的实现
 * @author: D
 * @create: 2020-06-24 15:51
 **/
@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private LoginUserServiceImpl loginUserServiceimpl;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*LoginUser loginUserInfo = loginUserServiceimpl.getLoginUserInfo(s);
*//*        UserDetails userDetails = User
                .withUsername(loginUserInfo.getUsername())
                .password(loginUserInfo.getPassword())
                .authorities(loginUserInfo.getPermissionsArr()).build();*/


        User dbUser = userMapper.selectByUsername(username);
        log.info("dbUser:"+dbUser);
        if (dbUser == null){
            //todo   抛一个用户不存在的异常
            log.info("用户不存在的异常");
        }else if (dbUser.getStatus() == com.you.springsecuritydemo.domain.pojo.User.Status.LOCKED){
            //todo   抛一个用户锁定的异常
            log.info("用户锁定");
        }else if (dbUser.getStatus() == User.Status.DISABLED){
            //todo   抛一个用户已作废的异常
            log.info("用户已作废");
        }


        LoginUser loginUserDto = new LoginUser();

        BeanUtils.copyProperties(dbUser,loginUserDto);
        log.info("loginUserDto:" + loginUserDto);
        List<Permission> dbPermissionList = permissionMapper.selectPermissionByUserId(dbUser.getId());
        log.info("dbPermissionList:" + dbPermissionList);

        loginUserDto.setPermissions(dbPermissionList);

        return loginUserDto;
    }
}
