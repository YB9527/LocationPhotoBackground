package com.xupu.zjd.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xupu.common.YBException.ZJDException;
import com.xupu.common.po.ResultData;
import com.xupu.common.service.ResultDataService;
import com.xupu.common.tools.FileTool;
import com.xupu.common.tools.Tool;
import com.xupu.usermanager.service.IUserService;
import com.xupu.xzqy.service.IXZDMService;
import com.xupu.zjd.po.ZJD;
import com.xupu.zjd.service.IPhotoService;
import com.xupu.zjd.service.IZJDService;
import com.xupu.zjd.service.ZJDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/zjd")
public class ZJDController {
    @Autowired
    private IZJDService zjdService;
    @Autowired
    private IPhotoService photoService;
    private static ResultDataService resultDataService = ResultDataService.getResultDataService();
    @Autowired
    private IXZDMService xzdmService;
    @Autowired
    private IUserService userService;


    @RequestMapping(value = "/downloadGeodatabase")
    public void downloadFile(String geodatabaseName, HttpServletRequest request, HttpServletResponse response) throws IOException {

        FileTool.DownLoadFile(response, ZJDService.geodatabasesDir + geodatabaseName);

    }


    /**
     * 查询所有地块
     *
     * @return
     */
    @RequestMapping(value = "/findall")
    public String findAll() {
        List<ZJD> zjds = zjdService.findAll();
        return Tool.objectToJson(zjds);
    }

    /**
     * 查询所有地块
     *
     * @return
     */
    @RequestMapping(value = "/findalltoresultdata")
    public ResultData findalltoresultdata() {
        List<ZJD> zjds = zjdService.findAll();
        return resultDataService.getSuccessResultData(zjds);
    }

    /**
     * 根据行政代码 查找 符合的宅基地
     *
     * @return
     */
    @PostMapping("/findbyxzdmids")
    @ResponseBody
    public ResultData findByXZDMIds(String xzdmids) {
        List<Long> xzdmidList = Tool.jsonToObject(xzdmids, new TypeToken<List<Long>>() {
        }.getType());
        List<ZJD> zjds = zjdService.findByXZDMIds(xzdmidList);
        ResultData resultData = resultDataService.getSuccessResultData(zjds);
        return resultData;
    }
    /**
     * 根据行政代码 查找 符合的宅基地
     *
     * @return
     */
    @RequestMapping("/findbyxzdmid")
    public ResultData findByXZDMId(Long xzdmid) {
        List<Long> xzdmidList = new ArrayList<>();
        xzdmidList.add(xzdmid);
        List<ZJD> zjds = zjdService.findByXZDMIds(xzdmidList);
        ResultData resultData = resultDataService.getSuccessResultData(zjds);
        return resultData;
    }
    /**
     * 根据 ZDNUM 查询 zjd
     *
     * @return
     */
    @RequestMapping(value = "/findbyzdnum")
    public String findAll(String ZDNUM) {
        if (Tool.isEmpty(ZDNUM)) {
            return "";
        }
        ZJD zjd = zjdService.findByZDNUM(ZDNUM);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(zjd);
    }


    /**
     * 根据 ZDNUM 查询 zjd
     *
     * @return
     */
    @RequestMapping(value = "/findbyzdnumresultdata")
    public ResultData findbyzdnum(String ZDNUM) {
        if (Tool.isEmpty(ZDNUM)) {
            return resultDataService.getErrorResultData("没有传输编码过来");
        }
        ZJD zjd = zjdService.findByZDNUM(ZDNUM);

        return resultDataService.getSuccessResultData(zjd);
    }


    /**
     * 保存宅基地
     *
     * @param zjds
     */
    @PostMapping("/savezjds")
    @ResponseBody
    public ResultData saveZJDs(String projectid, String zjds) {
        if (Tool.isEmpty(zjds)) {
            return resultDataService.getOtherResultData("没有需要保存的地块");
        }
        try {
            Long projectidL = Tool.parseLong(projectid);
            List<ZJD> zjdsList = new Gson().fromJson(zjds, new TypeToken<List<ZJD>>() {
            }.getType());
            return zjdService.saveOrUpdateAll2(projectidL, zjdsList);
        } catch (ZJDException e) {
            return resultDataService.getErrorResultData(e.getMessage());
        }

    }

    /**
     * 修改宅基地
     *
     * @param zjd
     */
    @PostMapping("/updatezjd")
    @ResponseBody
    public ResultData updateZJD(String zjd) {
        if (Tool.isEmpty(zjd)) {
            return resultDataService.getOtherResultData("没有需要保存的地块");
        }
        ZJD zjdPo = Tool.jsonToObject(zjd, ZJD.class);

        //修改照片路径
        ZJD oldZJD = zjdService.findById(zjdPo.getId());
        zjdPo.setXzdm(oldZJD.getXzdm());
        zjdPo.setXzdmid(oldZJD.getXzdmid());
        photoService.updatePhotoPath(oldZJD, zjdPo);
        zjdService.save(zjdPo);
        return resultDataService.getSuccessResultData("");
    }

    /**
     * 删除宅基地， 和 所属的照片
     *
     * @param po
     */
    @PostMapping("/deletezjd")
    @ResponseBody
    public ResultData deleteZJD(String po) {
        if (Tool.isEmpty(po)) {
            return resultDataService.getErrorResultData("没有需要删除地块");
        }
        ZJD zjdPo = Tool.jsonToObject(po, ZJD.class);
        zjdService.deleteZJD(zjdPo);
        photoService.deleteAllPhotoByZJD(zjdPo);
        return resultDataService.getSuccessResultData("");
    }

    @RequestMapping(value = "/deletezjdbyid")
    public ResultData deleteZJDByID(String idstr) {
        if (Tool.isEmpty(idstr)) {
            return resultDataService.getErrorResultData("没有携带id");
        }
        try {
            Long id = Long.parseLong(idstr);
            zjdService.deleteZJDByID(id);

            return resultDataService.getSuccessResultData("");
        } catch (Exception e) {
            return resultDataService.getErrorResultData("传入的不是数字id");
        }


    }


    @RequestMapping(value = "/test")
    public void test(HttpServletRequest request, HttpServletResponse response) {
        //List<XZDM> xzdms = xzdmService.findAll();
        String s="123";
        //xzdmService.saveOrUpdate(xzdms);
        s="123"+"5";
        s="123";
        List<ZJD> zjds =zjdService.findAll();
        List<ZJD> webzjds =Tool.jsonToObject(Tool.objectToJson(zjds),new TypeToken<List<ZJD>>() {
        }.getType());

        for (ZJD zjd : webzjds){
            zjdService.save(zjd);
        }

        s="123"+"5";
        s="123";
    }

    @PostMapping("/findzjdsbyxzdmanduser")
    @ResponseBody
    public ResultData findzjdsbyxzdm_user(String userid, String xzdmids) {
        Long id = null;
        if (!Tool.isEmpty(userid)) {
            id = Long.parseLong(userid);
        }
        List<Long> xzdmidList = Tool.jsonToObject(xzdmids, new TypeToken<List<Long>>() {
        }.getType());
        List<ZJD> zjds = zjdService.findByXZDMIds(id, xzdmidList);

        return resultDataService.getSuccessResultData(zjds);
    }

    /**
     * 行政区域内的宅基地 都分配工作人员
     *
     * @param zjds
     * @param userid
     * @return
     */
    @PostMapping(value = "/savesetuser")
    @ResponseBody
    public ResultData saveSetUser(String zjds, String userid) {
        List<ZJD> zjdList = Tool.getGson().fromJson(zjds, new TypeToken<List<ZJD>>() {
        }.getType());
        Long useridL;
        try {
            useridL = Tool.parseLong(userid);
        } catch (ZJDException e) {
            return resultDataService.getErrorResultData(e.getMessage());
        }

        return zjdService.SetUser(useridL, zjdList);

    }


}
