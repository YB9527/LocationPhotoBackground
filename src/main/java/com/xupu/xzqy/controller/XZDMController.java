package com.xupu.xzqy.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xupu.common.YBException.ZJDException;
import com.xupu.common.po.ResultData;
import com.xupu.common.po.Status;
import com.xupu.common.service.ResultDataService;
import com.xupu.common.tools.Tool;
import com.xupu.xzqy.po.XZDM;
import com.xupu.xzqy.service.IXZDMService;
import com.xupu.zjd.po.ZJD;
import com.xupu.zjd.service.IZJDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/xzdm")
public class XZDMController {
    @Autowired
    IXZDMService xzdmService;
    private static ResultDataService resultDataService = ResultDataService.getResultDataService();

    @Autowired
    private IZJDService zjdService;

    /**
     * 查询所有 行政代码
     *
     * @return
     */
    @RequestMapping(value = "/findall")
    public ResultData findAll() {
        List<XZDM> xzdms = xzdmService.findAll();
        return new ResultData(Status.Success, "成功", Tool.getGson().toJson(xzdms));
    }

    /**
     * 如果数据库中没有找到行政代码，那么就添加，如果有就更新，根据 djzqdm 来判断
     *
     * @param xzdms
     * @return
     */
    @PostMapping(value = "/checkxzdms")
    @ResponseBody
    public ResultData checkxzdms(String xzdms) {
        List<XZDM> xzdmList = Tool.getGson().fromJson(xzdms, new TypeToken<List<XZDM>>() {
        }.getType());
        if (Tool.isEmpty(xzdmList)) {
            return resultDataService.getErrorResultData("没有传任何数据过来");
        }
        ResultData resultData = xzdmService.checkXZDMList(xzdmList);
        if (resultData.getStatus() == Status.Error) {
            return resultData;
        }
        return resultDataService.getSuccessResultData("");
    }

    /**
     * 如果数据库中没有找到行政代码，那么就添加，如果有就更新，根据 djzqdm 来判断
     *
     * @param xzdms
     * @return
     */
    @PostMapping(value = "/savexzdms")
    @ResponseBody
    public ResultData save(String xzdms) {
        List<XZDM> xzdmList = Tool.getGson().fromJson(xzdms, new TypeToken<List<XZDM>>() {
        }.getType());
        ResultData resultData = checkxzdms(xzdms);
        //先检查数据
        if (resultData.getStatus() == Status.Error) {
            return resultData;
        } else {
            //保存数据
            return xzdmService.saveOrUpdate(xzdmList);
        }
    }
    /**
     * 导入行政代码表
     *
     * @return
     */
    @PostMapping(value = "/importzjds")
    @ResponseBody
    public ResultData importzjds(String projectid, String xzdms) {
        List<XZDM> xzdmList = Tool.getGson().fromJson(xzdms, new TypeToken<List<XZDM>>() {
        }.getType());
        if (Tool.isEmpty(xzdmList)) {
            return resultDataService.getErrorResultData("没有传任何数据过来");
        }
        try {
            Long id = Tool.parseLong(projectid);
            return xzdmService.importZJDs(id, xzdmList);
        } catch (ZJDException e) {
            return resultDataService.getErrorResultData(e.getMessage());
        }
        //return resultDataService.getSuccessResultData("");
    }


    /**
     * 只能删除没有关联的行政代码
     * @param xzdms
     * @return
     */
    @PostMapping(value = "/deletexzdms")
    @ResponseBody
    public ResultData deletexzdms(String xzdms) {
        List<XZDM> xzdmList = Tool.getGson().fromJson(xzdms, new TypeToken<List<XZDM>>() {
        }.getType());
        if (Tool.isEmpty(xzdmList)) {
            return resultDataService.getErrorResultData("没有传任何数据过来");
        }
        xzdmService.deleteXZDMs(xzdmList);
        return resultDataService.getSuccessResultData("");
    }

    /**
     * 行政区域内的宅基地 都分配工作人员
     * @param xzdms
     * @param userid
     * @return
     */
    @PostMapping(value = "/savesetuser")
    @ResponseBody
    public ResultData saveSetUser(String xzdms,Long userid) {
        List<XZDM> xzdmList = new Gson().fromJson(xzdms, new TypeToken<List<XZDM>>() {
        }.getType());
        List<Long> xzdmids = new ArrayList<>();
        for (XZDM xzdm :xzdmList
             ) {
            xzdmids.add(xzdm.getId());
        }
        List<ZJD> zjds = zjdService.findByXZDMIds(xzdmids);
        return zjdService.SetUser(userid,zjds);

    }
    @RequestMapping(value = "/findbyprojectid")
    public ResultData findByProjectId(Long projectid) {
        List<XZDM> xzdms = xzdmService.findByProjectId(projectid);

       return resultDataService .getSuccessResultData(xzdms);
    }
}
