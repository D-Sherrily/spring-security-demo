package com.you.springsecuritydemo.service.impl;

import com.you.springsecuritydemo.annotation.Log;
import com.you.springsecuritydemo.domain.dto.SysLogDetailDto;
import com.you.springsecuritydemo.domain.pojo.User;
import com.you.springsecuritydemo.mapper.UserMapper;
import com.you.springsecuritydemo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: UserServiceImpl
 * @Description: userService 的实现类
 * @author: D
 * @create: 2020-07-22 17:13
 **/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    @Log("修改")
    public void modifyPasswordByUsername(User user ) {
        if (user != null){
            User dbuser = userMapper.selectByUsername(user.getUsername());
            int resultSet = userMapper.updatePasswordByUsername(user);
            SysLogDetailDto sysLogDetailDto = new SysLogDetailDto();
            sysLogDetailDto.setColumn("password");
            sysLogDetailDto.setNewColumnContent(user.getPassword());
            sysLogDetailDto.setOldColumnContent(dbuser.getPassword());
            List<SysLogDetailDto> logDetailDtoList = new ArrayList<>();
            logDetailDtoList.add(sysLogDetailDto);

            ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
            attributes.getRequest().getSession().setAttribute("logDetailDtoList",logDetailDtoList);

        }
    }
}
