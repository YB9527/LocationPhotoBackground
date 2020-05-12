package com.xupu.xzqy.dao;

import com.xupu.xzqy.po.XZDM;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface XZDMRepository extends JpaRepository<XZDM,Long> {

    List<XZDM> findAll(Specification<XZDM> orderSpecification);
    List<XZDM> findByProjectid(Long projectid);
}
