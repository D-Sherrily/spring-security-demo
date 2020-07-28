package com.you.springsecuritydemo.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;



@Slf4j
public class SmsAuthenticationProvider implements AuthenticationProvider {
    @Getter
    @Setter
    private UserDetailsService userDetailsService;

    public SmsAuthenticationProvider() {
        log.info("SmsAuthenticationProvider  loading ...");

    }


    /**
     * @Description 认证
     **/
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取过滤器封装的token信息
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken)authentication;
        //获取手机号
        String mobile = (String) authenticationToken.getPrincipal();

        //获取用户信息（数据库认证）
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        //不通过
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("Unable to obtain user information");
        }
        //通过
        SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(userDetails,userDetails.getAuthorities());
        //鉴权成功后返回一个新的SmsAuthenticationToken
        authenticationResult.setDetails(authenticationToken.getDetails());


        return authenticationResult;
    }

    /**
     * @Description  根据token类型 判断使用的provider
     **/
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }




    private void checkSmsCode(String mobile) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String inputCode = request.getParameter("smsCode");

        Map<String, Object> smsCode = (Map<String, Object>) request.getSession().getAttribute("smsCode");
        if(smsCode == null) {
            throw new BadCredentialsException("未检测到申请验证码");
        }

        String applyMobile = (String) smsCode.get("mobile");
        int code = (int) smsCode.get("code");

        if(!applyMobile.equals(mobile)) {
            throw new BadCredentialsException("申请的手机号码与登录手机号码不一致");
        }
        if(code != Integer.parseInt(inputCode)) {
            throw new BadCredentialsException("验证码错误");
        }
    }


}

