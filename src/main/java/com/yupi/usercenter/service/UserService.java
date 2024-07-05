package com.yupi.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.usercenter.model.domain.User;
import jakarta.servlet.http.HttpServletRequest;


/**
 * 用户服务
 *
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return 用户 id
     */
    long userRegister(String userAccount,String userPassword,String checkPassword,String planetCode);


    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @return 脱敏后的用户信息 User
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 根据 id 获取脱敏后的用户信息
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);
}
