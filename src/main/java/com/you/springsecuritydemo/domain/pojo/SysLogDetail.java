package com.you.springsecuritydemo.domain.pojo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLogDetail {
    private Integer id;

    private Integer logId;

    private String tableName;

    private String operationAction;

    private String columnName;

    private String oldColumnContent;

    private String newColumnContent;

    private Date createtime;

    private Integer isDelete;
}