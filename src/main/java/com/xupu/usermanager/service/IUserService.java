package com.xupu.usermanager.service;

import com.xupu.common.po.ResultData;
import com.xupu.usermanager.po.User;

public interface IUserService {
    /**
     * 登录
     *
     * @param user 前台 登录 user
     * @return 数据库中查找到的 user
     */
    User login(User user);


    /**
     * 注册账号
     * @param userPo
     * @return
     */
    ResultData registUser(User userPo);
}
