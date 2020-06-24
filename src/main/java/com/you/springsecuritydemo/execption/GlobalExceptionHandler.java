package com.you.springsecuritydemo.execption;

import com.you.springsecuritydemo.domain.entity.Resp;
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
    //todo

    @ExceptionHandler(value = Exception.class)
    public Resp globalException(){
        Resp<Object> exceptionResp = new Resp<>();

        //exceptionResp.setMsg();
        return exceptionResp;
    }
}
