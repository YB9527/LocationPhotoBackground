package com.xupu.xzqy.service;

import com.xupu.common.YBException.ZJDException;
import com.xupu.common.po.ResultData;
import com.xupu.common.service.ResultDataService;
import com.xupu.common.tools.ReflectTool;
import com.xupu.common.tools.Tool;
import com.xupu.project.po.Project;
import com.xupu.project.service.IProjectService;
import com.xupu.xzqy.dao.XZDMRepository;
import com.xupu.xzqy.po.XZDM;
import com.xupu.zjd.po.ZJD;
import com.xupu.zjd.service.ZJDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class XZDMService implements IXZDMService {
    @Autowired
    private XZDMRepository xzdmRepository;
    @Autowired
    private ZJDService zjdService;
    @Autowired
    private IProjectService projectService;
    private static ResultDataService resultDataService = ResultDataService.getResultDataService();
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
        try {
            djzqdmMap = ReflectTool.getIDMap("getDJZQDM", getXzdms());
        } catch (ZJDException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, XZDM> djzqdmMap;

    /**
     * 得到 静态的 djzqdm map集合
     *
     * @return
     */
    public Map<String, XZDM> getDJZQDMMap() {
        if (djzqdmMap == null) {
            try {
                djzqdmMap = ReflectTool.getIDMap("getDJZQDM", getXzdms());
            } catch (ZJDException e) {
                e.printStackTrace();
            }
        }
        return djzqdmMap;
    }

    @Override
    public Map<String, XZDM> getDJZQDMMap(List<XZDM> xzdms) {
        try {
            return ReflectTool.getIDMap("getDJZQDM", xzdms);
        } catch (ZJDException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultData checkXZDMList(List<XZDM> xzdmList) {
        List<String> errs = new ArrayList<>();
        String err;
        for (XZDM xzdm : xzdmList) {
            if (Tool.isEmpty(xzdm.getDJZQDM())) {
                err = "行政区域编码有空";
                if (!errs.contains(err)) {
                    errs.add(err);
                }
            }
            if (Tool.isEmpty(xzdm.getDJZQMC())) {
                err = "行政区域名称有空";
                if (!errs.contains(err)) {
                    errs.add(err);
                }
            }
        }
        //检查djzqdm是否重复
        try {
            Map<String, XZDM> xzdmMap = ReflectTool.getIDMap("getDJZQDM", xzdmList);
        } catch (ZJDException e) {
            err = e.getMessage();
            if (!errs.contains(err)) {
                errs.add(err);
            }
        }
        if (errs.size() > 0) {
            return resultDataService.getErrorResultData(Tool.listToString(errs, ","));
        }
        return resultDataService.getSuccessResultData("");
    }

    @Override
    public ResultData saveOrUpdate(List<XZDM> newxzdms) {
        List<XZDM> oldxzdms = findAll();
        return saveOrUpdate(oldxzdms, newxzdms);

    }

    @Override
    public ResultData saveOrUpdate(List<XZDM> oldxzdms, List<XZDM> newxzdms) {
        Map<String, XZDM> oldxzdmMap = null;
        try {
            oldxzdmMap = ReflectTool.getIDMap("getDJZQDM", oldxzdms);
        } catch (ZJDException e) {
            return resultDataService.getErrorResultData("以前数据库中XZDM中DJZQDM重复:" + e.getMessage());
        }
        XZDM oldxzdm;
        String djzqdm;
        List<XZDM> savexzdms = new ArrayList<>();
        for (XZDM newxzdm : newxzdms) {
            djzqdm = newxzdm.getDJZQDM();
            oldxzdm = oldxzdmMap.get(djzqdm);
            if (oldxzdm != null) {
                //交换数据
                oldxzdm.setDJZQMC(newxzdm.getDJZQMC());
                newxzdm = oldxzdm;
            }
            savexzdms.add(newxzdm);
        }
        xzdmRepository.saveAll(savexzdms);
        return resultDataService.getSuccessResultData("");
    }

    @Override
    public ResultData saveSetUser(List<XZDM> xzdmList) {

        xzdmRepository.saveAll(xzdmList);
        return resultDataService.getSuccessResultData("");
    }

    @Override
    public ResultData saveZJDs(List<XZDM> xzdmList) {
        xzdmRepository.saveAll(xzdmList);
        return resultDataService.getSuccessResultData("");
    }

    @Override
    public ResultData importZJDs(Long projectid, List<XZDM> xzdmList) {
        Project project = projectService.findById(projectid);
        for (XZDM xzdm : xzdmList) {
            xzdm.setProject(project);
            for (ZJD zjd : xzdm.getZjds()) {
                zjd.setXzdmid(xzdm.getId());
                zjd.setXzdm(xzdm);
            }
        }
        return saveZJDs(xzdmList);
    }



    @Override
    public List<XZDM> findByProjectId(Long projectid) {
        List<XZDM> list = xzdmRepository.findByProjectid(projectid);
        return list;
    }

    @Transactional
    @Override
    public void deleteXZDMs(List<XZDM> xzdmList) {
        //只检查在宅基地中没有用到的
        List<ZJD> zjds = zjdService.findAll();

        Set<Long> xzdmids = new HashSet<>();
        for (int i = 0; i < zjds.size(); i++) {
           xzdmids.add(zjds.get(i).getXzdmid());
        }
        for (XZDM xzdm : xzdmList) {
            if(!xzdmids.contains(xzdm.getId())){
                xzdmRepository.delete(xzdm);
            }
        }


    }

    @Override
    public List<XZDM> findAll() {
        return xzdmRepository.findAll();
    }


}
