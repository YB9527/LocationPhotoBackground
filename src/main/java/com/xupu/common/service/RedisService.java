package com.xupu.common.service;


import com.google.gson.reflect.TypeToken;
import com.xupu.common.Tool;
import com.xupu.common.dao.RedisRepository;
import com.xupu.common.po.Redis;
import com.xupu.xzqy.dao.XZDMRepository;
import com.xupu.xzqy.po.XZDM;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RedisService  implements  IRedisService{
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private XZDMRepository xzdmRepository;
    @Override
    public String findByUserIdAndMark(Long userid, String mark) {

        Redis cache = redisRepository.findByUserIdAndMark(userid,mark);
        if(cache != null){
            return  cache.getJson();
        }
        return "";
    }

    @Override
    public void saveOrUpdate(long userid, String mark, String data) {
        Redis redis = redisRepository.findByUserIdAndMark(userid,mark);
        if (redis == null){
            //如果没有是保存到数据库
            redisRepository.save(new Redis(userid,mark,data));
        }else{
            //如果有是修改json数据
            redis.setJson(data);
            redisRepository.save(redis);
        }
    }

    @Override
    public List<XZDM> findSelectXZDM(Long userid, String mark) {
        String json = findByUserIdAndMark(userid,mark);
        if(json == null){
            return  null;
        }
        List<String> djzqdms = Tool.getGson().fromJson(json,new TypeToken<List<String>>(){}.getType());
        StringBuilder sb = new StringBuilder();
        for (String djzqdm: djzqdms
             ) {

            //sb.append("")
        }
        return null;
    }
}
