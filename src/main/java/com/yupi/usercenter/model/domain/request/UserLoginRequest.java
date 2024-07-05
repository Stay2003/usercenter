package com.yupi.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
//9135184846199560240L
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 9135184846199560240L;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    // https://www.code-nav.cn/
}
