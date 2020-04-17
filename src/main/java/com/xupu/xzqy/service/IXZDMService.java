package com.xupu.xzqy.service;

import com.xupu.xzqy.po.XZDM;

import java.util.List;

public interface IXZDMService {
    /**
     * 查询让你的的 行政区域
     * @return
     */
    List<XZDM> findAll();
}
