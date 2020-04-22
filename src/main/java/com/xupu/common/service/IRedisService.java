package com.xupu.common.service;

import com.xupu.common.po.Redis;
import com.xupu.xzqy.po.XZDM;

import javax.persistence.Cache;
import java.util.List;

public interface IRedisService {
    /**
     * 查找出 json
     * @param userid
     * @param mark
     * @return
     */
    String findByUserIdAndMark(Long userid, String mark);

    /**
     * 检查 或 保存 redis
     * @param userid
     * @param mark
     * @param data
     */
    void saveOrUpdate(Long userid, String mark, String data);

    List<String> findSelectXZDM(Long userid, String mark);

    Redis findByMark(String mark);
}
