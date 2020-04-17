package com.xupu.zjd.service;

import com.xupu.zjd.dao.ZJDRepository;
import com.xupu.zjd.po.ZJD;
import com.xupu.common.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ZJDService implements IZJDService {
    @Autowired
    private ZJDRepository zjdRepository;



    @Override
    public List<ZJD> findAll() {
        return zjdRepository.findAll();
    }

    @Override
    public void saveAll(List<ZJD> ZJDS) {
        if(!Tool.isEmpty(ZJDS)){
            zjdRepository.saveAll(ZJDS);
        }

    }

    @Override
    public void save(ZJD ZJD) {
        if(ZJD != null){
            zjdRepository.save(ZJD);
        }
    }

    @Override
    public void delete(List<ZJD> ZJDS) {
        if(!Tool.isEmpty(ZJDS)){
            zjdRepository.deleteAll(ZJDS);
        }

    }

    public ZJD findById(Long id) {
        if(id == null){
            return  null;
        }
        ZJD zjd =  zjdRepository.findById(id).get();
        return zjd;
    }

    @Override
    public ZJD findByZDNUM(String zdnum) {
        List<ZJD> zjds =  zjdRepository.findByZDNUM(zdnum);
        if(!Tool.isEmpty(zjds)){
            return  zjds.get(0);
        }
        return null;
    }


}
