package com.you.springsecuritydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TestController
 * @Description: 测试用的controller
 * @author: D
 * @create: 2020-06-23 16:34
 **/
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "123";
    }
}
