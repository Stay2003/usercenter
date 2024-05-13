package com.yupi.usercenter.controller;

import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.request.UserRegisterRequest;
import com.yupi.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口
 *
 * @author stay_2003
 */
@RestController //返回值默认是json格式
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if(StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)){
            return null;
        }
        Long id = userService.userRegister(userAccount, userPassword, checkPassword);
        return id;
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserRegisterRequest userRegisterRequest, HttpServletRequest request) {
        //校验
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount, userPassword)){
            return null;
        }
        return userService.userLogin(userAccount, userPassword,request);

    }
}


