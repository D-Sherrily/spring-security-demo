package com.you.springsecuritydemo;

import com.you.springsecuritydemo.domain.pojo.SysLog;
import com.you.springsecuritydemo.domain.pojo.SysLogDetail;
import com.you.springsecuritydemo.service.SysLogService;
import com.you.springsecuritydemo.utils.CosUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class SpringSecurityDemoApplicationTests {

    @Resource
    private SysLogService sysLogService;

    @Resource
    private CosUtil uploadUtil;
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


    @Test
    void testLogService() {
        SysLog sysLog = new SysLog(null,1,"删除",null,1);
        SysLogDetail sysLogDetail = new SysLogDetail();
        sysLogDetail.setColumnName("姓名");
        sysLogDetail.setLogId(1);
        sysLogDetail.setNewColumnContent("qwe");
        sysLogDetail.setOldColumnContent("123");
        sysLogService.insertLog(sysLog,sysLogDetail);
    }


    @Test
    void testCosUpload() {
        String filePath = "C:\\Users\\dell\\IdeaProjects\\spring-security-demo\\src\\main\\resources\\static\\img\\logo\\logo.png";
        String filename = "logo1.png";
        uploadUtil.cosUpload(filePath,filename);
    }

    @Test
    void testCosFileList() {

        uploadUtil.cosGetFileList();
    }


    @Test
    void testCosDownLoad() {
        String filename = "logo1.png";
        String targetFile = "C:\\Users\\dell\\IdeaProjects\\spring-security-demo\\src\\main\\resources\\11\\logo.png";
        uploadUtil.cosDownLoad(filename);
    }

    @Test
    void testCosDel() {
        String filename = "logo1.png";
        uploadUtil.cosDel(filename);
    }
}
