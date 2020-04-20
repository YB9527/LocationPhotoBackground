package com.xupu.common.tools;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class RepositoryTool {

    public  static <T> Specification<T> getSpecification(String cloumn, List<String> list) {

        Specification<T> sp = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> listOr = new ArrayList<Predicate>();
                for (String str : list
                ) {
                    Predicate p1 = cb.like(root.get(cloumn), str+"%");
                    listOr.add(cb.or(p1));
                }
                Predicate[] preOr = new Predicate[listOr.size()];
                Predicate or = cb.or(listOr.toArray(preOr));
                query.where(or);
                return null;
            }
        };
        return sp;
    }

}
