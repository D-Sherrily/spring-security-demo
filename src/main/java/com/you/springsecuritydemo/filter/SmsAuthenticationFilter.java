package com.you.springsecuritydemo.filter;

import com.you.springsecuritydemo.config.SmsAuthenticationToken;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import sun.security.util.SecurityConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: SmsAuthenticationfilter
 * @Description: 短信
 * @author: D
 * @create: 2020-07-28 14:17
 **/


@Slf4j
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private boolean postOnly = true;

    @Getter
    @Setter
    private String mobileParameterName;

    public SmsAuthenticationFilter(String mobileParameterName) {
        super(new AntPathRequestMatcher("login/sms","POST"));
        this.mobileParameterName = mobileParameterName;
        log.info("SmsAuthenticationFilter  loading ...");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        //获取手机号
        String mobile = obtainMobile(request);

        //获取token
        SmsAuthenticationToken authRequest = new SmsAuthenticationToken(mobile);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);


    }

    /**
     * @Description 设置身份认证详情信息
     **/
    protected void setDetails(HttpServletRequest request,
                              SmsAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }




    /**
     * 获取手机号
     */
    private String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameterName);
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}

