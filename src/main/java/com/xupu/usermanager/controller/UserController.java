package com.xupu.usermanager.controller;

import com.xupu.common.Tool;
import com.xupu.usermanager.po.User;
import com.xupu.usermanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * 登录用户
     * @return 数据中查找到的user
     */
    @PostMapping("/login")
    @ResponseBody
    public User login(String user) {
        User userPo = Tool.getGson().fromJson(user,User.class);
        if(user == null || Tool.isEmpty(userPo.getAccount()) || Tool.isEmpty(userPo.getPassword())){
            return null;
        }
        return userService.login(userPo);
    }


}
