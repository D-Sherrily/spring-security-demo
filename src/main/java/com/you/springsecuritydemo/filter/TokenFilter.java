package com.you.springsecuritydemo.filter;

import com.you.springsecuritydemo.domain.constants.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: TokenFilter
 * @Description: 获取token的过滤器
 * @author: D
 * @create: 2020-06-24 15:21
 **/

/*
@Component
public class TokenFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String jwtToken = request.getParameter(Constant.JWT_TOKEN_KEY);
        if (StringUtils.isBlank(jwtToken)){
            jwtToken = request.getHeader(Constant.JWT_TOKEN_KEY);
        }
        //从jwtToken里面获取用户信息
        //todo

    }
}
*/
