package com.you.springsecuritydemo.service;

import com.you.springsecuritydemo.domain.pojo.SysLog;
import com.you.springsecuritydemo.domain.pojo.SysLogDetail;

/**
 * @ClassName: SysLogService
 * @Description: SysLogService
 * @author: D
 * @create: 2020-07-23 11:10
 **/

public interface SysLogService {
    void insertLog(SysLog sysLog, SysLogDetail sysLogDetail);
}
