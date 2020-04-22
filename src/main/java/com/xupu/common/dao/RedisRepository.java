package com.xupu.common.dao;

import com.xupu.common.po.Redis;
import com.xupu.usermanager.po.User;
import org.springframework.data.jpa.repository.JpaRepository;


import javax.persistence.Entity;


public interface RedisRepository extends JpaRepository<Redis,Long> {

    Redis findByUserIdAndMark(Long  userId, String mark);

    Redis findByMark(String mark);
}
