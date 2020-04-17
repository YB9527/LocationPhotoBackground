package com.xupu.common.service;

import javax.persistence.Cache;

public interface IRedisService {
    String findByUserIdAndMark(String userid, String mark);
}
