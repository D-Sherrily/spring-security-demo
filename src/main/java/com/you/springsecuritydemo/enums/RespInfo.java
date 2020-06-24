package com.you.springsecuritydemo.enums;

import com.you.springsecuritydemo.basic.BaseExceptionInfoInterface;
import com.you.springsecuritydemo.domain.constants.Constant;

/**
 * @ClassName: RespInfo
 * @Description: 异常信息枚举
 * @author: D
 * @create: 2020-06-24 14:05
 **/

public enum RespInfo implements BaseExceptionInfoInterface {


    //成功
    SUCCESS(Constant.SUCCESS_CODE,"成功"),
    //权限异常
    UN_AUTH(Constant.UN_AUTH_CODE,"权限不足&未授权"),

    //token缺失
    ILLEGAL_TOKEN(Constant.ILLEGAL_TOKEN_CODE,"无效的token"),
    SERvER_BUSY(Constant.FAIL_CODE,"服务器繁忙");

    /** 错误码 */
    private Integer respCode;

    /** 错误描述 */
    private String respMsg;

    RespInfo(Integer respCode,String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }


    @Override
    public Integer getRespCode() {
        return respCode;
    }

    @Override
    public String getRespMsg() {
        return respMsg;
    }
}
