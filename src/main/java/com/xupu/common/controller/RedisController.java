package com.xupu.common.controller;

import com.xupu.common.Tool;

import com.xupu.common.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/redis")
public class RedisController {

    @Autowired
    private IRedisService redisService;

    @GetMapping("/findredis")
    public String login(String userid,String mark) {

        if(Tool.isEmpty(userid) || Tool.isEmpty(mark)  ){
            return  "";
        }
        return redisService.findByUserIdAndMark(userid,mark);
    }
}
