package com.xupu.xzqy.service;

import com.xupu.common.tools.ReflectTool;
import com.xupu.xzqy.dao.XZDMRepository;
import com.xupu.xzqy.po.XZDM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class XZDMService implements IXZDMService {
    @Autowired
    XZDMRepository xzdmRepository;

    private static List<XZDM> xzdms;

    /**
     * 得到 静态的行政代码
     *
     * @return
     */
    public List<XZDM> getXzdms() {
        if (xzdms == null) {
            xzdms = findAll();
        }
        return xzdms;
    }

    /**
     * 更新 静态的  xzdms 和 djzqdmMap 两个集合
     */
    public void flushXzdms() {
        xzdms = findAll();
        djzqdmMap = ReflectTool.getIDMap("getDJZQDM", getXzdms());
    }

    private static Map<String, XZDM> djzqdmMap;

    /**
     * 得到 静态的 djzqdm map集合
     *
     * @return
     */
    public Map<String, XZDM> getDJZQDMMap() {
        if (djzqdmMap == null) {
            djzqdmMap = ReflectTool.getIDMap("getDJZQDM", getXzdms());
        }
        return djzqdmMap;
    }

    @Override
    public List<XZDM> findAll() {
        return xzdmRepository.findAll();
    }


}
