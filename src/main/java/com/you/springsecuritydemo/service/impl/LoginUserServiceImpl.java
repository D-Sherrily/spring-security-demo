package com.you.springsecuritydemo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName: LoginUserService
 * @Description: 用户登录处理的service  实现了userDetailsService
 * @author: D
 * @create: 2020-06-24 10:11
 **/
@Slf4j
@Service
public class LoginUserServiceImpl {
  /*  @Resource
    private UserMapper userMapper;

    @Resource
    private PermissionMapper permissionMapper;

    public LoginUser getLoginUserInfo(String username){
        LoginUser loginUserDto = new LoginUser();
        ArrayList<String> permissionList = new ArrayList<>();
        User dbUser = userMapper.selectByUsername(username);
        log.info("dbUser:"+dbUser);
        if (dbUser == null){
            //todo   抛一个用户不存在的异常
            log.info("用户不存在的异常");
        }else if (dbUser.getStatus() == User.Status.LOCKED){
            //todo   抛一个用户锁定的异常
            log.info("用户锁定");
        }else if (dbUser.getStatus() == User.Status.DISABLED){
            //todo   抛一个用户已作废的异常
            log.info("用户已作废");
        }


        BeanUtils.copyProperties(dbUser,loginUserDto);
        log.info("loginUserDto:" + loginUserDto);
        List<Permission> dbPermissionList = permissionMapper.selectPermissionByUserId(dbUser.getId());
        log.info("dbPermissionList:" + dbPermissionList);
        dbPermissionList.forEach(c -> permissionList.add(c.getPermission()));
        List<String> permissionListFinal = permissionList.stream().distinct().collect(Collectors.toList());
        String[] permissionArr = new String[permissionListFinal.size()];
        loginUserDto.setPermissionsArr(permissionListFinal.toArray(permissionArr));
        return loginUserDto;
    }*/
}
