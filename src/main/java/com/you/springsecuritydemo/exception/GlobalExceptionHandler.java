package com.you.springsecuritydemo.exception;

import com.you.springsecuritydemo.domain.entity.Resp;
import com.you.springsecuritydemo.enums.RespInfo;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName: ExceptionHandlerAdvice
 * @Description: 全局 异常处理
 * @author: D
 * @create: 2020-06-24 13:43
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = IllegalTokenException.class)
    public Resp exceptionHandler(Exception e){
        Resp<Object> exceptionResp = new Resp<>();

        exceptionResp.setMsg(e.getMessage());
        exceptionResp.setData(null);
        exceptionResp.setStatus(RespInfo.ILLEGAL_TOKEN.getRespCode());
        return exceptionResp;
    }
}
