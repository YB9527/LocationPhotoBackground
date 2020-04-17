package com.xupu.xzqy.service;

import com.xupu.xzqy.dao.XZDMRepository;
import com.xupu.xzqy.po.XZDM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XZDMService implements  IXZDMService {
    @Autowired
    XZDMRepository xzdmRepository;

    @Override
    public List<XZDM> findAll() {
        return xzdmRepository.findAll();
    }
}
