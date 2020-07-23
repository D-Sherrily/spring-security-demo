package com.you.springsecuritydemo.controller;

import com.you.springsecuritydemo.domain.pojo.User;
import com.you.springsecuritydemo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @ClassName: UserController
 * @Description: 用户 controller
 * @author: D
 * @create: 2020-07-22 15:07
 **/

@Api(tags = "用户")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("saveUser")
    @ApiOperation(value = "保存用户")
    @PreAuthorize("hasAnyAuthority('sys:user:manager','sys:user:query','sys:user:password')")
    public String saveUser(){

        return "saveUser";
    }


    @PostMapping("delUser")
    @ApiOperation(value = "删除用户")
    @PreAuthorize("hasAuthority('sys:file:del')")
    public String deleteUser(){
        return "delUser";
    }


    @PostMapping("modifyPassword")
    @ApiOperation(value = "修改密码")
    @PreAuthorize("hasAnyAuthority('sys:user:modify')")
    public String modifyUserPassword(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(BCrypt.hashpw(password,BCrypt.gensalt()));
        userService.modifyPasswordByUsername(user);
        return "modifyPassword";
    }

}
