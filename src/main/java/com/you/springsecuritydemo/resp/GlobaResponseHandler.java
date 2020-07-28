package com.you.springsecuritydemo.resp;

import com.alibaba.fastjson.JSON;
import com.you.springsecuritydemo.annotation.IgnorReponseAdvice;
import com.you.springsecuritydemo.domain.entity.Resp;
import com.you.springsecuritydemo.enums.RespInfo;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @ClassName: GlobaResponseHandler
 * @Description: 全局统一返回值处理
 * @author: D
 * @create: 2020-07-27 13:44
 **/
@RestControllerAdvice
public class GlobaResponseHandler implements ResponseBodyAdvice {
    //判断支持的类型
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        //检查是否有 @IgnorReponseAdvice 注解  有则忽略拦截
        //判断类上有没有注解
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnorReponseAdvice.class)){
            return false;
        }
        //判断 方法上有没有注解
        if(methodParameter.getMethod().isAnnotationPresent(IgnorReponseAdvice.class)){
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        Resp<Object> resp = new Resp<>();

        if(o instanceof Resp){
            return o;
        }

        resp.setStatus(RespInfo.SUCCESS.getRespCode());
        resp.setData(o);
        resp.setMsg(RespInfo.SUCCESS.getRespMsg());
        //String 类型的特殊处理
        if (o instanceof String){
            return JSON.toJSON(resp.toString());
        }


        return resp;
    }
}
