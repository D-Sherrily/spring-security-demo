package com.you.springsecuritydemo.service.impl;

import com.you.springsecuritydemo.domain.constants.Constant;
import com.you.springsecuritydemo.domain.entity.LoginUser;
import com.you.springsecuritydemo.domain.entity.TokenDto;
import com.you.springsecuritydemo.service.TokenService;
import io.jsonwebtoken.*;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: TokenServiceImpl
 * @Description: token service 的实现类  （使用JWT方式）
 * @author: D
 * @create: 2020-07-21 16:38
 **/
@Slf4j
@Service
public class TokenServiceJWTImpl implements TokenService {

    /**从配置文件中获取 token 过期时间*/
    @Value("${token.expire.seconds}")
    private Integer expireSeconds;

    @Value("${token.expire.jwtSecret}")
    private String jwtSecret;


    @Resource
    private RedisTemplate<String,LoginUser> redisTemplate;

    public static  Key KEY = null;


    //保存jwttoken
    @Override
    public TokenDto saveToken(LoginUser loginUser) {
        loginUser.setToken(UUID.randomUUID().toString());
        //缓存用户信息
        cacheLoginUser(loginUser);
        //生成 jwtkoken
        String jwtToken = createJWTToken(loginUser);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(jwtToken);
        tokenDto.setLoginTime(loginUser.getLoginTime());
        return tokenDto;
    }

    //刷新缓存的用户信息
    @Override
    public void refresh(LoginUser loginUser) {
        cacheLoginUser(loginUser);
    }

    //根据token 获取用户信息
    @Override
    public LoginUser getLoginUser(String jwtToken) {
        String uuid = getUUIDFromJWT(jwtToken);
        if (uuid != null){
            return redisTemplate.boundValueOps(getTokenKey(uuid)).get();
        }

        return null;
    }

    @Override
    public boolean deleteToken(String jwtToken) {
        String uuid = getUUIDFromJWT(jwtToken);
        if (uuid != null){
            String key = getTokenKey(uuid);
            LoginUser loginUser = redisTemplate.opsForValue().get(key);
            if (loginUser != null){
                redisTemplate.delete(key);
                return true;
            }
        }
        return false;
    }

    //缓存登录信息到redis里面
    private void cacheLoginUser(LoginUser loginUser){
        //设置当前时间
        loginUser.setLoginTime(System.currentTimeMillis());
        //设置过期时间
        loginUser.setExpireTime(loginUser.getLoginTime()+expireSeconds);
        //根据uuid 将loginUser缓存
        redisTemplate.boundValueOps(getTokenKey(loginUser.getToken())).set(loginUser,expireSeconds, TimeUnit.SECONDS);
    }



    private String getTokenKey(String uuid){
        return "tokens: "+uuid;
    }


    //生成 jwtkoken
    private String createJWTToken(LoginUser loginUser){
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(Constant.LOGIN_USER_KEY,loginUser.getToken());
        String jwtToken = Jwts.builder().
                setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();

        return jwtToken;

    }

    //获取KEY  ??
    private Key getKeyInstance(){
        if (KEY == null){
            synchronized (TokenServiceJWTImpl.class){
                //双重锁
                if (KEY == null){
                    byte[] apiKeySecretsBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
                    KEY = new SecretKeySpec(apiKeySecretsBytes, SignatureAlgorithm.HS256.getJcaName());
                }
            }
        }
        return KEY;
    }

    private String getUUIDFromJWT(String jwtToken){
        if ("null".equals(jwtToken) || StringUtils.isBlank(jwtToken)){
            return null;
        }
        try {
            Map<String, Object> claims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwtToken).getBody();
            return MapUtils.getString(claims, Constant.LOGIN_USER_KEY);
        }
        catch (ExpiredJwtException e) {
            log.error("{}已过期", jwtToken);
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }
}
