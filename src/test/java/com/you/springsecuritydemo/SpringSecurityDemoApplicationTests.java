package com.you.springsecuritydemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class SpringSecurityDemoApplicationTests {

    @Test
    void contextLoads() {
        String hashpw = BCrypt.hashpw("123456", BCrypt.gensalt());
        System.out.println(hashpw);
    }

    @Test
    void testDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy-MM-dd HH");
        Date date = new Date();
        System.out.println(date);
    }

}
