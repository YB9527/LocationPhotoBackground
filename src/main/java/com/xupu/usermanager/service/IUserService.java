package com.xupu.usermanager.service;

import com.xupu.usermanager.po.User;

public interface IUserService {
    /**
     *登录
     * @param user 前台 登录 user
     * @return  数据库中查找到的 user
     */
    User login(User user);
}
