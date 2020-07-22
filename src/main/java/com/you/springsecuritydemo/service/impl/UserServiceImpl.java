package com.you.springsecuritydemo.service.impl;

import com.you.springsecuritydemo.domain.pojo.User;
import com.you.springsecuritydemo.mapper.UserMapper;
import com.you.springsecuritydemo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: UserServiceImpl
 * @Description: userService 的实现类
 * @author: D
 * @create: 2020-07-22 17:13
 **/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public void modifyPasswordByUsername(User user) {
        if (user != null){
            int resultSet = userMapper.updatePasswordByUsername(user);

        }
    }
}
