package com.you.springsecuritydemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: Log
 * @Description: 自定义注解  用来定义切点  方法级别的注解
 * @author: D
 * @create: 2020-07-21 17:10
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    String value() default "";
}
