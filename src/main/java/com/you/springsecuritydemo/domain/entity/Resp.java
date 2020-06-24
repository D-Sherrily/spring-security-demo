package com.you.springsecuritydemo.domain.entity;

import com.you.springsecuritydemo.enums.RespInfo;
import lombok.Data;

/**
 * @ClassName: Resp
 * @Description: 统一返回值类
 * @author: D
 * @create: 2020-06-24 14:05
 **/

@Data
public class Resp<T> {

    private int status = RespInfo.SUCCESS.getRespCode();
    private String msg = RespInfo.SUCCESS.getRespMsg();
    private T data = null;
}
