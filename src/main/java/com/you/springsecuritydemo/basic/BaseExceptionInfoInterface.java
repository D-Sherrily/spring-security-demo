package com.you.springsecuritydemo.basic;

/**
 * @ClassName: BaseExceptionInfoInterface
 * @Description: 基础全局异常处理接口
 * @author: D
 * @create: 2020-06-24 14:05
 **/

public interface BaseExceptionInfoInterface {

    /**
     * @Description  获取错误码
     **/
    Integer getRespCode();


    /**
     * @Description  获取错误信息
     **/
    String getRespMsg();
}
