package com.yupi.usercenter.model.request;

import java.io.Serial;
import java.io.Serializable;

public class UserLoginRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 9135184846199560240L;

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

    private String userAccount;
    private String userPassword;
}
