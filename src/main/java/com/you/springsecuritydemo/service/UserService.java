package com.you.springsecuritydemo.service;

import com.you.springsecuritydemo.domain.pojo.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: UserService
 * @Description: user service
 * @author: D
 * @create: 2020-07-22 17:13
 **/

public interface UserService {

    void modifyPasswordByUsername(User user);
}
