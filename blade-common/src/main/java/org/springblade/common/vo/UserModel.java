package org.springblade.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data

/**
 * 用户WebToken与DefaultToken存入redis信息对象
 */
public class UserModel implements Serializable {

    //用户GUID
    private String userGUID;

    //用户代码
    private String userCode;

    //tokenType类型
    private String tokenType;

    //clientIP
    private String clientIP;

    //browserInfo
    private String browserInfo;

}
