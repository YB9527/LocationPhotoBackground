package com.xupu.zjd.service;

import com.xupu.common.po.ResultData;
import com.xupu.common.tools.SpringBootTool;
import com.xupu.zjd.po.ZJD;

import java.util.List;

public interface IZJDService {

    /**
     *离线数据包的跟路径
     */
    public static String geodatabasesDir = SpringBootTool.getRootDir() + "zjd/geodatabases/";
    /**
     * 查找所有地块
     */
    List<ZJD> findAll();

    /**
     * 必须是19位的编码
     *
     * @param ZJDS
     */
    void saveOrUpdateAll( List<ZJD> ZJDS);

    /**
     * 可以不是 19位的编码
     * @param projectId
     * @param ZJDS
     */
    ResultData saveOrUpdateAll2(Long projectId, List<ZJD> ZJDS);

    void save(ZJD ZJD);

    void delete(List<ZJD> ZJDS);

    ZJD findById(Long id);

    /**
     * 根据宗地编码查找数据
     * @param zdnum
     * @return
     */
    ZJD findByZDNUM(String zdnum);

    /**
     * 根据行政代码 查找 符合的宅基地
     * @param xzdmids
     * @return
     */
    List<ZJD> findByXZDMIds(List<Long> xzdmids);
    List<ZJD> findByXZDMid(Long xzdmid);

    /**
     * 修改在基地
     * @param zjdPo
     */
    void update(ZJD zjdPo);

    /**
     * 根据 id 和 djzqdm 查找种地， 如果id == null 返回所有符合 djzqdms 的宗地
     * @param id
     * @param xzdmids
     * @return
     */
    List<ZJD> findByXZDMIds(Long id,  List<Long> xzdmids);

    /**
     * 删除宅基地
     * @param zjdPo
     */
    void deleteZJD(ZJD zjdPo);

    /**
     * festure 的json 数据 转换为宅基地
     * @param featureJsons
     * @return
     */
    List<ZJD> jsonToZJD(List<String> featureJsons);

    /**
     * 检查在基地的问题
     * @param zjds
     * @return 错误的问题
     */
    List<String> checkZJDs(List<ZJD> zjds);

    void deleteZJDByID(Long id);

    /**
     * 宅基地设置工作人员
     * @param userid
     * @param zjds
     */
    ResultData SetUser(Long userid, List<ZJD> zjds);
}
