package com.xupu.zjd.dao;

import com.xupu.zjd.po.ZJD;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ZJDRepository extends JpaRepository<ZJD,Long> {

    @Override

    @Query(value = "SELECT objectid ,is_upload,project_id,xzdm_id,user_id,zdnum,bz,quanli,djzqdm,st_astext(shape) as geometry FROM zjd ", nativeQuery = true)
    List<ZJD> findAll();

    /**
     * 保存 多个地块
     * @param iterable
     * @param <S>
     * @return
     */
    @Override
    <S extends ZJD> List<S> saveAll(Iterable<S> iterable);

    /**
     *保存 或者 修改 单个地块
     * @param s
     * @param <S>
     * @return
     */
    @Override
    <S extends ZJD> S saveAndFlush(S s);


    @Override
    Optional<ZJD> findById(Long aLong);

    List<ZJD> findByZDNUM(String ZDNUM);

    List<ZJD> findAll(Specification<ZJD> orderSpecification);

}
