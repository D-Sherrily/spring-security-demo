
package com.you.springsecuritydemo.config;

import com.you.springsecuritydemo.filter.SmsAuthenticationFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @ClassName: SmsAuthenticationSecurityConfig
 * @Description: config
 * @author: D
 * @create: 2020-07-28 15:15
 **/


@Component
@Configuration
public class SmsAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Resource
    private AuthenticationFailureHandler authenticationFailureHandler;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        String parameter = "phone";
        String defaultParameter= "phone";

        //创建并配置好自定义SmsAuthenticationfilter
        SmsAuthenticationFilter SmsAuthenticationFilter = new SmsAuthenticationFilter(
                StringUtils.isBlank(parameter) ? defaultParameter : parameter);

        SmsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        SmsAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        SmsAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        //创建并配置好自定义SmsAuthenticationProvide
        SmsAuthenticationProvider sysLoginAuthenticationProvider = new SmsAuthenticationProvider();
        sysLoginAuthenticationProvider.setUserDetailsService(userDetailsService);
        //将过滤器添加到过滤链路中
        http.authenticationProvider(sysLoginAuthenticationProvider)
                .addFilterAfter(SmsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


}


