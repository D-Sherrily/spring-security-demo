package com.you.springsecuritydemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

/**
 * @ClassName: SmsAuthenticationToken
 * @Description: sms token
 * @author: D
 * @create: 2020-07-28 14:28
 **/

@Slf4j
public class SmsAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 7001684796506408168L;

    private final Object principal;


    public SmsAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        this.setAuthenticated(false);
        log.info("SmsAuthenticationToken setAuthenticated ->false loading ...");
    }


    public SmsAuthenticationToken(Object principal,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
        log.info("SmsAuthenticationToken setAuthenticated ->true loading ...");

    }


    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated){
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }


    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}

