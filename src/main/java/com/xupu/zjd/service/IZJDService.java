package com.xupu.zjd.service;

import com.xupu.common.SpringBootTool;
import com.xupu.zjd.po.ZJD;

import java.util.List;
import java.util.Map;

public interface IZJDService {

    /**
     *离线数据包的跟路径
     */
    public static String geodatabasesDir = SpringBootTool.getRootDir() + "zjd/geodatabases/";
    /**
     * 查找所有地块
     */
    List<ZJD> findAll();

    void saveAll(List<ZJD> ZJDS);

    void save(ZJD ZJD);

    void delete(List<ZJD> ZJDS);

    ZJD findById(Long id);

}
