package com.xupu.admin.service;

import com.xupu.common.po.Redis;
import com.xupu.common.service.IRedisService;
import com.xupu.common.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService  implements  IAdminService{
    @Autowired
    private IRedisService redisService;
    //redis 中 后台密码的  mark
    public static  String adminMark = "admin";




    @Override
    public String findAdminPassword() {
        Redis redis = redisService.findByMark(adminMark);
        String password ;
        if(redis == null || Tool.isEmpty(redis.getJson())){
            password = "123";
            redisService.saveOrUpdate(null,adminMark,password);
        }else{
            password = redis.getJson();
        }
        return password;
    }
}
