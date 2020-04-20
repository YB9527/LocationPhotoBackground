package com.xupu.xzqy.dao;

import com.xupu.xzqy.po.XZDM;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Query;
import javax.persistence.criteria.Order;
import java.util.List;

public interface XZDMRepository extends JpaRepository<XZDM,Long> {

    List<XZDM> findAll(Specification<XZDM> orderSpecification);
}
