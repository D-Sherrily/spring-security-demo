package com.you.springsecuritydemo.domain.dto;

import com.you.springsecuritydemo.domain.pojo.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: UserDto
 * @Description: 用户登录的实体类
 * @author: D
 * @create: 2020-06-24 09:43
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto implements Serializable {
    private static final long serialVersionUID = -5911645384918125716L;

    private Integer id;

    private String username;

    private String password;

    private String[] permissionsArr;

    private String token;
    /**登陆时间戳（毫秒）**/
    private Long loginTime;

    /** 过期时间戳 **/
    private Long expireTime;
}
