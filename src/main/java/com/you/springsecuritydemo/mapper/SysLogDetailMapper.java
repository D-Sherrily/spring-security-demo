package com.you.springsecuritydemo.mapper;

import com.you.springsecuritydemo.domain.pojo.SysLogDetail;

public interface SysLogDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLogDetail record);

    int insertSelective(SysLogDetail record);

    SysLogDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLogDetail record);

    int updateByPrimaryKey(SysLogDetail record);
}