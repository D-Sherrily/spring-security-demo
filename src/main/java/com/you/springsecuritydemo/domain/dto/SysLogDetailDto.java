package com.you.springsecuritydemo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLogDetailDto {


    private Integer logId;

    private String tableName;

    private String operationAction;

    private String columnName;

    private String column;

    private String oldColumnContent;

    private String newColumnContent;


}