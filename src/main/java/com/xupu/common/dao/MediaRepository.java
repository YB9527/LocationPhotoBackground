package com.xupu.common.dao;

import com.xupu.common.po.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media,Long> {
    //List<Media> findByPro
}
