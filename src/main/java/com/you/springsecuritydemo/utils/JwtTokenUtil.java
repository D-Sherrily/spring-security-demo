package com.you.springsecuritydemo.utils;

import com.you.springsecuritydemo.domain.constants.Constant;
import com.you.springsecuritydemo.domain.dto.LoginUserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.HashMap;

/**
 * @ClassName: JWTTokenUtils
 * @Description: jwtTokenUtil
 * @author: D
 * @create: 2020-06-24 16:27
 **/

public class JwtTokenUtil {
    /**
     * @Description 生成 jwt 令牌
     **/
    public static String createJwtToken(LoginUserDto loginUser){
        HashMap<String, Object> claims = new HashMap<>();
        //放入一个随机字符串 ，通过该字符串可以找到登录用户
        claims.put(Constant.LOGIN_USER_KEY,loginUser.getToken());
/*        Jwts
                .builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,get)*/
        return null;
    }
}
