package com.yupi.usercenter.model.request;

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
    private String userAccount;
    private String userPassword;
    private String checkPassword;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }
}
