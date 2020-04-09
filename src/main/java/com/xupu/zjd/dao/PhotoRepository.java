package com.xupu.zjd.dao;


import com.xupu.zjd.po.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface PhotoRepository extends JpaRepository<Photo,Long> {

    @Override
    List<Photo> findAll();
    /**
     * 通过路径查找 photo
     * @param path
     * @return
     */
    Photo findByPath(String path);

    /**
     * 保存 多个照片
     * @param iterable
     * @param <S>
     * @return
     */
    @Override
    <S extends Photo> List<S> saveAll(Iterable<S> iterable);

    /**
     *保存单个照片
     * @param s
     * @param <S>
     * @return
     */
    @Override
    <S extends Photo> S saveAndFlush(S s);

}
