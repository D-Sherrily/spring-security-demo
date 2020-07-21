package com.you.springsecuritydemo.service;

import com.you.springsecuritydemo.domain.entity.LoginUser;
import com.you.springsecuritydemo.domain.entity.TokenDto;

/**
 * @ClassName: TokenService
 * @Description: token service
 * @author: D
 * @create: 2020-07-20 15:51
 **/

public interface TokenService {

    TokenDto saveToken(LoginUser loginUser);

    void refresh(LoginUser loginUser);

    LoginUser getLoginUser(String token);

    boolean deleteToken(String token);
}
