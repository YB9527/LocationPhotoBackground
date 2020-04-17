package com.xupu.common.service;


import com.xupu.common.dao.RedisRepository;
import com.xupu.common.po.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class RedisService  implements  IRedisService{
    @Autowired
    private RedisRepository redisRepository;

    @Override
    public String findByUserIdAndMark(String userid, String mark) {

        Redis cache = redisRepository.findByUserIdAndMark(userid,mark);
        if(cache != null){
            return  cache.getJson();
        }
        return "";
    }
}
