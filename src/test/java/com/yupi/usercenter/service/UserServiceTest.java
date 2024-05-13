package com.yupi.usercenter.service;

import com.yupi.usercenter.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        // 测试添加用户的方法
        User user = new User();
        user.setUsername("test");
        user.setUseraccount("test");
        user.setAvatarurl("https://example.com/avatar.jpg");
        user.setGender(0);
        user.setUserpassword("123456");
        user.setPhone("12345678901");
        user.setEmail("test@example.com");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);


    }

    @Test
    void userRegister() {
        String userAccount = "test02";
        String userPassword = "";
        String checkPassword = "123456";
        Long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "02";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "test 02";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "test03";
        userPassword = "12345678";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertTrue(result>0);
    }


}