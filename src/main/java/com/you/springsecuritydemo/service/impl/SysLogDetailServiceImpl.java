package com.you.springsecuritydemo.service.impl;

import com.you.springsecuritydemo.domain.pojo.SysLog;
import com.you.springsecuritydemo.domain.pojo.SysLogDetail;
import com.you.springsecuritydemo.exception.MyException;
import com.you.springsecuritydemo.mapper.SysLogDetailMapper;
import com.you.springsecuritydemo.mapper.SysLogMapper;
import com.you.springsecuritydemo.service.SysLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @ClassName: SysLogDetailServiceImpl
 * @Description: 日志 操作
 * @author: D
 * @create: 2020-07-23 11:17
 **/
@Service
public class SysLogDetailServiceImpl implements SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    @Resource
    private SysLogDetailMapper sysLogDetailMapper;


    @Transactional(propagation= Propagation.REQUIRED,rollbackFor= MyException.class)
    @Override
    public void insertLog(SysLog sysLog, SysLogDetail sysLogDetail) {
        if (sysLog != null && sysLogDetail != null){
            sysLogMapper.insert(sysLog);
            sysLogDetail.setLogId(sysLog.getId());
            sysLogDetailMapper.insert(sysLogDetail);
        }

    }
}
