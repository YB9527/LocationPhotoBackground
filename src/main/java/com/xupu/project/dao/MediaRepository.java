package com.xupu.project.dao;

import com.xupu.project.po.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media,Long> {
    //List<Media> findByPro
}
