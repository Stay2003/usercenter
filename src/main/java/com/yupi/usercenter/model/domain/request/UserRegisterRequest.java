package com.yupi.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登录请求体
 * @author stay_2003
 */
@Data
public class UserRegisterRequest implements Serializable {


    @Serial
    private static final long serialVersionUID = -6903241795004046926L;
    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 校验密码
     */
    private String checkPassword;

    /**
     * 星球编号
     */
    private String planetCode;
}
