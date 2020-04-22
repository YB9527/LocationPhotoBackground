package com.xupu.usermanager.service;

import com.alibaba.fastjson.JSONObject;
import com.xupu.common.po.ResultData;
import com.xupu.usermanager.po.Level;
import com.xupu.usermanager.po.User;

import java.util.List;
import java.util.Map;

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

    /**
     * 查找所有的用户
     * @return
     */
    List<User> findAll();

    /**
     * 编辑用户
     * @param userPo
     * @return
     */
    ResultData editUser(User userPo);

    User findById(Long id);

    List<JSONObject> findLevels();
}
