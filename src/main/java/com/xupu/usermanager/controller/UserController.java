package com.xupu.usermanager.controller;

import com.xupu.common.po.ResultData;
import com.xupu.common.service.ResultDataService;
import com.xupu.common.tools.Tool;
import com.xupu.usermanager.po.Level;
import com.xupu.usermanager.po.User;
import com.xupu.usermanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private IUserService userService;

    private static ResultDataService resultDataService = ResultDataService.getResultDataService();

    /**
     * 登录用户
     *
     * @return 数据中查找到的user
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultData login(String user) {
        User userPo = Tool.getGson().fromJson(user, User.class);
        if (user == null || Tool.isEmpty(userPo.getAccount()) || Tool.isEmpty(userPo.getPassword())) {
            return resultDataService.getErrorResultData();
        }
        userPo = userService.login(userPo);
        if (userPo == null) {
            return resultDataService.getErrorResultData("登录失败！！！");
        }
        if (userPo.getLevel() == null) {
            return resultDataService.getErrorResultData(userPo.getNickName() + "，你好，管理员，还没有同意注册，请稍等!!!");
        }
        return resultDataService.getSuccessResultData(userPo);

    }

    /**
     * 注册账号
     *
     * @return
     */
    @PostMapping("/regist")
    @ResponseBody
    public ResultData regist(String user) {
        User userPo = Tool.getGson().fromJson(user, User.class);

        return userService.registUser(userPo);
    }

    /**
     * 查找所有的注册用户
     *
     * @return
     */
    @RequestMapping(value = "/findregistusers")
    public ResultData findregistusers() {

        List<User> userList = userService.findRegistUsers();
        if(userList.size() == 0){
            List<User> users = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                User user = new User(i + 1 + "", "YB" + (i + 1), i + 1 + "", "52@");
                user.setLevel(Level.employee);
                users.add(user);
            }
            userService.saveUsers(users);
            userList = userService.findRegistUsers();
        }
        return resultDataService.getSuccessResultData(userList);
    }

    /**
     * 查找所有的用户
     *
     * @return
     */
    @RequestMapping(value = "/findusers")
    public ResultData findusers() {

        List<User> userList = userService.findAll();
        return resultDataService.getSuccessResultData(userList);
    }

    /**
     * 编辑用户
     *
     * @return
     */
    @PostMapping("/edituser")
    @ResponseBody
    public ResultData editUser(String po) {
        User userPo = Tool.getGson().fromJson(po, User.class);
        return userService.editUser(userPo);
    }

    /**
     * 查找 用户管理等级
     *
     * @return
     */
    @RequestMapping(value = "/findlevels")
    public ResultData findlevels() {
        return resultDataService.getSuccessResultData(userService.findLevels());
    }
}
