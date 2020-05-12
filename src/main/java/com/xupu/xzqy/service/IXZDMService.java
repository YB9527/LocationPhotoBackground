package com.xupu.xzqy.service;

import com.xupu.common.po.ResultData;
import com.xupu.xzqy.po.XZDM;
import com.xupu.zjd.po.ZJD;

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
    /**
     * key 为 djzqdm 的行政代码映射
     * @param xzdms
     * @return
     */
    Map<String, XZDM> getDJZQDMMap(List<XZDM> xzdms);
    /**
     * 检查 行政代码
     * @param xzdmList
     * @return
     */
    ResultData checkXZDMList(List<XZDM> xzdmList);

    /**
     * 保存或者修改行政代码
     * @param newxzdms
     */
    ResultData saveOrUpdate(List<XZDM> newxzdms);

    /**
     * 只能删除没有关联的行政代码
     * @param xzdmList
     */
    void deleteXZDMs(List<XZDM> xzdmList);

    /**
     * 保存或者修改行政代码
     * @param oldxzdms 数据库中地块
     * @param newxzdms 新添加的地块
     * @return
     */
     ResultData saveOrUpdate(List<XZDM> oldxzdms, List<XZDM> newxzdms);

    /**
     * 保存 行政代码的 user
     * @param xzdmList
     * @return
     */
    ResultData saveSetUser(List<XZDM> xzdmList);


    /**
     * 保存里面的在基地
     * @param xzdmList
     * @return
     */
    ResultData saveZJDs(List<XZDM> xzdmList);

    /**
     * 导入项目行政代码
     * @param projectid
     * @param xzdmList
     * @return
     */
    ResultData importZJDs(Long projectid, List<XZDM> xzdmList);

    /**
     * 得到行政区内的所有宅基地
     * @param xzdmList
     * @return
     */
    List<ZJD> getZJDAll(List<XZDM> xzdmList);

    /**
     * 通过项目id 得到行政区域
     * @param projectid
     * @return
     */
    List<XZDM> findByProjectId(Long projectid);
}
