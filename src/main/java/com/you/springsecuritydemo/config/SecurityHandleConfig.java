package com.you.springsecuritydemo.config;

import com.you.springsecuritydemo.domain.entity.LoginUser;
import com.you.springsecuritydemo.domain.entity.Resp;
import com.you.springsecuritydemo.domain.entity.TokenDto;
import com.you.springsecuritydemo.service.TokenService;
import com.you.springsecuritydemo.utils.JwtTokenUtil;
import com.you.springsecuritydemo.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: SecurityHandleConfig
 * @Description: security配置的辅助信息
 * @author: D
 * @create: 2020-07-20 14:27
 **/
@Slf4j
@Configuration
public class SecurityHandleConfig {
    @Resource
    private TokenService tokenService;

    /**
     * @Description 登录成功，返回token
     **/
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler(){
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                LoginUser loginUser = (LoginUser)authentication.getPrincipal();
                log.info("loginUser:"+loginUser);
                TokenDto tokenDto = tokenService.saveToken(loginUser);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                ResponseUtil.responseJson(httpServletResponse, HttpStatus.OK.value(),tokenDto);
            }
        };
    }

    /**
     * @Description 登录失败
     **/
    @Bean
    public AuthenticationFailureHandler loginFailureHandler(){
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                String msg = null;
                if (e instanceof BadCredentialsException){
                    msg = "密码错误";
                }else {
                    msg = e.getMessage();
                }
                Resp<String> resp = new Resp<>();
                resp.setMsg(msg);
                resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                ResponseUtil.responseJson(httpServletResponse,HttpStatus.UNAUTHORIZED.value(),resp);

            }
        };
    }

    /**
     * @Description 未登录，返回错误值  401
     **/
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                Resp<String> resp = new Resp<>();
                resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                resp.setMsg("请先登录");
                ResponseUtil.responseJson(httpServletResponse,HttpStatus.UNAUTHORIZED.value(),resp);
            }
        };
    }

    /**
     * @Description 退出登录成功
     **/
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                Resp<String> resp = new Resp<>();
                resp.setMsg("退出成功");
                resp.setStatus(HttpStatus.OK.value());
                //todo token 失效  删除token
                ResponseUtil.responseJson(httpServletResponse,HttpStatus.OK.value(),resp);
            }
        };
    }


}
