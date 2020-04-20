package com.xupu.zjd.service;

import com.xupu.common.tools.RepositoryTool;
import com.xupu.xzqy.po.XZDM;
import com.xupu.xzqy.service.IXZDMService;
import com.xupu.xzqy.service.XZDMService;
import com.xupu.zjd.dao.ZJDRepository;
import com.xupu.zjd.po.ZJD;
import com.xupu.common.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ZJDService implements IZJDService {
    @Autowired
    private ZJDRepository zjdRepository;
    @Autowired
    private IXZDMService xzdmService;


    @Override
    public List<ZJD> findAll() {
        return zjdRepository.findAll();
    }

    @Override
    public void saveAll(List<ZJD> zjds) {
        if (!Tool.isEmpty(zjds)) {
            Map<String, XZDM> xzdmMap = xzdmService.getDJZQDMMap();
            for (ZJD zjd : zjds
            ) {
                String zdnum = zjd.getZDNUM();
                if (zdnum.length() == 19) {
                    String djzqdm = zdnum.replace("JA", "").replace("JB", "JC");
                    XZDM xzdm = xzdmMap.get(djzqdm.substring(0, 14));
                    zjd.setXzdm(xzdm);
                    zjd.setDJZQDM(djzqdm);
                }
            }
            zjdRepository.saveAll(zjds);
        }
    }

    @Override
    public void save(ZJD ZJD) {
        if (ZJD != null) {

            zjdRepository.save(ZJD);
        }
    }

    @Override
    public void delete(List<ZJD> ZJDS) {
        if (!Tool.isEmpty(ZJDS)) {
            zjdRepository.deleteAll(ZJDS);
        }

    }

    public ZJD findById(Long id) {
        if (id == null) {
            return null;
        }
        ZJD zjd = zjdRepository.findById(id).get();
        return zjd;
    }

    @Override
    public ZJD findByZDNUM(String zdnum) {
        List<ZJD> zjds = zjdRepository.findByZDNUM(zdnum);
        if (!Tool.isEmpty(zjds)) {
            return zjds.get(0);
        }
        return null;
    }

    @Override
    public List<ZJD> findByDJZQDM(List<String> djzqdms) {
        if (djzqdms == null) {
            return findAll();
        }

        Specification sp = RepositoryTool.getSpecification("DJZQDM", djzqdms);
        List<ZJD> list = zjdRepository.findAll(sp);
        return list;
    }

    @Override
    public void update(ZJD zjdPo) {

    }


}
