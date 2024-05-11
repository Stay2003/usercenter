package com.yupi.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yupi.usercenter.mapper.UserMapper;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * 用户服务实现类
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //1.校验
        if(StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)){
            return -1;   //参数不能为空
        }
        if (userAccount.length()<4 || userAccount.length()>16) {
            return -1;   //用户账号长度不正确
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8){
            return -1;   //密码长度不能小于8位
        }
        if(!userPassword.equals(checkPassword)){
            return -1;   //两次输入的密码不一致
        }
        //校验特殊字符
        if(!userAccount.matches("^[a-zA-Z0-9]+$")){
            return -1;   //用户名只能包含字母和数字
        }

        //用户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.count(queryWrapper);
        if(count>0){
            return -1;   //用户已存在
        }

        //2.加密密码
        final String SALT = "yupi";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());

        //3.写入数据库
        User user = new User();
        user.setUseraccount(userAccount);
        user.setUserpassword(encryptPassword);
        boolean result = this.save(user);
        if (!result){
            return -1;   //注册失败
        }


        //返回用户id
        return user.getId();
    }
}