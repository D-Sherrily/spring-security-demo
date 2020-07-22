package com.you.springsecuritydemo.domain.pojo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLog {
    private Integer id;

    private Integer userId;

    private String operation;

    private Date cratetime;

    private Integer isDelete;
}