package com.yupi.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yupi.usercenter.mapper.UserMapper;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import static com.yupi.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现类
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserMapper userMapper;

    // 盐（为了增加密码的复杂度）
    private final static String SALT = "stay_2003";
    // 登录状态保持的cookie名称


    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            //todo 返回自定义异常信息
            return -1;   //参数不能为空
        }
        if (userAccount.length() < 4 || userAccount.length() > 16) {
            return -1;   //用户账号长度不正确
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;   //密码长度不能小于8位
        }
        if (!userPassword.equals(checkPassword)) {
            return -1;   //两次输入的密码不一致
        }
        //校验特殊字符
        if (!userAccount.matches("^[a-zA-Z0-9]+$")) {
            return -1;   //用户名只能包含字母和数字
        }
        //用户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.count(queryWrapper);
        if (count > 0) {
            return -1;   //用户已存在
        }

        //2.加密密码
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //3.写入数据库
        User user = new User();
        user.setUseraccount(userAccount);
        user.setUserpassword(encryptPassword);
        boolean result = this.save(user);
        if (!result) {
            return -1;   //注册失败
        }
        //返回用户id
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;   //参数不能为空
        }
        if (userAccount.length() < 4 || userAccount.length() > 16) {
            return null;   //用户账号长度不正确
        }
        if (userPassword.length() < 8) {
            return null;   //密码长度不能小于8位
        }
        //校验特殊字符
        if (!userAccount.matches("^[a-zA-Z0-9]+$")) {
            return null;   //用户名只能包含字母和数字
        }

        //2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);

        //用户不存在
        if (user == null) {
            log.info("用户不存在");
            return null;   //用户数据错误
        }

        //3.用户信息脱敏
        User safetyUser = getSafetyUser(user);

        //4.记录用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        //5.返回查询结果
        return safetyUser;
    }



    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null){
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUseraccount(originUser.getUseraccount());
        safetyUser.setAvatarurl(originUser.getAvatarurl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserstatus(originUser.getUserstatus());
        safetyUser.setCreatetime(originUser.getCreatetime());
        safetyUser.setUpdatetime(originUser.getUpdatetime());
        safetyUser.setUserrole(originUser.getUserrole());
        return safetyUser;
    }
}