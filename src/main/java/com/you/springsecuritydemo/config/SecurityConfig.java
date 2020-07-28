package com.you.springsecuritydemo.config;

import com.you.springsecuritydemo.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;

/**
 *
 * @ClassName: SecurityConfig
 * @Description: spring security 的配置类
 * @author: D
 * @create: 2020-06-23 16:38
 **/
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Resource
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;

    @Resource
    private UserDetailsService userDetailsService;


    @Resource
    private TokenFilter tokenFilter;

   /* @Resource
    private SmsAuthenticationFilter smsAuthenticationFilter;*/

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //禁止csrf防护
        http.csrf().disable();
        //基于token  不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/", "/*.html", "/favicon.ico", "/css/**", "/js/**", "/fonts/**", "/layui/**", "/img/**",
                "/pages/**", "/druid/**", "/statics/**").permitAll()
                .antMatchers("/test/**").permitAll()
                .anyRequest().authenticated();

        http.formLogin().loginProcessingUrl("/login/**")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

        http.logout().logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler);



        //创建并配置好自定义SmsAuthenticationfilter，

      /*  //smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        smsAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        smsAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        //创建并配置好自定义SmsAuthenticationProvide
        SmsAuthenticationProvider smsAuthenticationProvide=new SmsAuthenticationProvider();
        smsAuthenticationProvide.setUserDetailsService(userDetailsService);
        http.authenticationProvider(smsAuthenticationProvide);
        //将过滤器添加到过滤链路中
        http.addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);*/

        //todo  加入tokenfilter
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        //http.addFilterBefore(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);




    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

}
