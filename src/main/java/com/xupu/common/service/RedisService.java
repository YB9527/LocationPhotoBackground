package com.xupu.common.service;


import com.google.gson.reflect.TypeToken;
import com.xupu.common.tools.Tool;
import com.xupu.common.dao.RedisRepository;
import com.xupu.common.po.Redis;
import com.xupu.xzqy.dao.XZDMRepository;
import com.xupu.xzqy.po.XZDM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class RedisService implements IRedisService {
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private XZDMRepository xzdmRepository;

    @Override
    public String findByUserIdAndMark(Long userid, String mark) {

        Redis cache = redisRepository.findByUserIdAndMark(userid, mark);
        if (cache != null) {
            return cache.getJson();
        }
        return "";
    }

    @Override
    public void saveOrUpdate(Long userid, String mark, String data) {
        Redis redis = redisRepository.findByUserIdAndMark(userid, mark);
        if (redis == null) {
            //如果没有是保存到数据库
            redisRepository.save(new Redis(userid, mark, data));
        } else {
            //如果有是修改json数据
            redis.setJson(data);
            redisRepository.save(redis);
        }
    }


    @Override
    public List<String> findSelectXZDM(Long userid, String mark) {
        String json = findByUserIdAndMark(userid, mark);
        if (json == null) {
            return null;
        }
        List<String> djzqdms = Tool.getGson().fromJson(json, new TypeToken<List<String>>() {
        }.getType());

        List<XZDM> list = xzdmRepository.findAll(new Specification<XZDM>() {
            @Override
            public Predicate toPredicate(Root<XZDM> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> listOr = new ArrayList<Predicate>();
                for (String djzqdm : djzqdms
                ) {
                    Predicate p1 = cb.equal(root.get("DJZQDM"), djzqdm);
                    listOr.add(cb.or(p1));
                }
                Predicate[] preOr = new Predicate[listOr.size()];
                Predicate or = cb.or(listOr.toArray(preOr));
                query.where(or);
                return null;
            }
        });
        List<String> reults = new ArrayList<>();
        for (XZDM xzdm : list
        ) {
            if(!reults.contains(xzdm.getDJZQDM()))
            {
                reults.add(xzdm.getDJZQDM());
            }

        }

        for (int i = 0; i < reults .size(); i++)    {
            for (int j = reults .size()-1; j > i; j--)  {
                String no= reults .get(j);
                String no_1= reults .get(j-1);
                if (no.compareTo(no_1)<0)    {
                    //互换位置
                    String  stu = reults.get(j);
                    reults.set(j, reults.get(j-1));
                    reults.set(j-1, stu );
                }
            }
        }
        return reults;
    }

    @Override
    public Redis findByMark(String mark) {
        return redisRepository.findByMark(mark);
    }
}
