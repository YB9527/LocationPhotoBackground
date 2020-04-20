package com.xupu.xzqy.service;

import com.xupu.xzqy.po.XZDM;

import java.util.List;
import java.util.Map;

public interface IXZDMService {
    /**
     * 查询让你的的 行政区域
     * @return
     */
    List<XZDM> findAll();
    /**
     * 更新 静态的  xzdms 和 djzqdmMap 两个集合
     */
     void flushXzdms();
    /**
     * 得到 静态的行政代码
     *
     * @return
     */
    List<XZDM> getXzdms();
    /**
     * 得到 静态的 djzqdm map集合
     *
     * @return
     */
    Map<String, XZDM> getDJZQDMMap();

}
