package com.you.springsecuritydemo.mapper;

import com.you.springsecuritydemo.domain.pojo.Token;

public interface TokenMapper {
    int deleteByPrimaryKey(String id);

    int insert(Token record);

    int insertSelective(Token record);

    Token selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Token record);

    int updateByPrimaryKey(Token record);


}