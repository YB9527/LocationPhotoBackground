package com.xupu.usermanager.controller;

import com.xupu.common.tools.Tool;
import com.xupu.common.po.ResultData;
import com.xupu.common.service.ResultDataService;
import com.xupu.usermanager.po.User;
import com.xupu.usermanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private IUserService userService;

    private static ResultDataService resultDataService= ResultDataService.getResultDataService();

    /**
     * 登录用户
     * @return 数据中查找到的user
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultData login(String user) {
        User userPo = Tool.getGson().fromJson(user,User.class);
        if(user == null || Tool.isEmpty(userPo.getAccount()) || Tool.isEmpty(userPo.getPassword())){
            return resultDataService.getErrorResultData();
        }
        userPo =  userService.login(userPo);
        return  resultDataService.getSuccessResultData(userPo);
    }



}
