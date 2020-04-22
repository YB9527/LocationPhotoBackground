package com.xupu.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.xupu.admin.service.IAdminService;
import com.xupu.common.po.Redis;
import com.xupu.common.po.ResultData;
import com.xupu.common.po.Status;
import com.xupu.common.service.IRedisService;
import com.xupu.common.service.RedisService;
import com.xupu.common.service.ResultDataService;
import com.xupu.common.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 请求后台
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private IRedisService redisService;
    @Autowired
    private IAdminService adminService;
    private ResultDataService resultDataService = ResultDataService.getResultDataService();

    /**
     * 后台登录
     * 密码不能设置为null，不然无法登录
     *
     * @param po
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultData login(String po) {
        if (Tool.isEmpty(po)) {
            return resultDataService.getErrorResultData("没有输入字符");
        }
        JSONObject jsonObject = JSONObject.parseObject(po);
        String password = adminService.findAdminPassword();
        if (password.equals(jsonObject.getString("password"))) {
            return resultDataService.getSuccessResultData(null);
        } else {
            return resultDataService.getErrorResultData("密码不正确");
        }
    }

}
