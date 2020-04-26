package com.xupu.zjd.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xupu.common.po.ResultData;
import com.xupu.common.service.ResultDataService;
import com.xupu.common.tools.FileTool;
import com.xupu.common.tools.Tool;
import com.xupu.xzqy.po.XZDM;
import com.xupu.zjd.po.ZJD;
import com.xupu.zjd.po.Photo;
import com.xupu.zjd.service.IZJDService;
import com.xupu.zjd.service.IPhotoService;
import com.xupu.zjd.service.ZJDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping(value = "/zjd")
public class ZJDController {
    @Autowired
    private IZJDService zjdService;
    @Autowired
    private IPhotoService photoService;
    private static ResultDataService resultDataService= ResultDataService.getResultDataService();


    @RequestMapping(value = "/downloadGeodatabase")
    public void downloadFile(String geodatabaseName, HttpServletRequest request, HttpServletResponse response) throws IOException {

        FileTool.DownLoadFile(response, ZJDService.geodatabasesDir + geodatabaseName);

    }

    /**
     * 查询所有地块
     * @return
     */
    @RequestMapping(value = "/findall")
    public String  findAll() {
        List<ZJD> zjds =  zjdService.findAll();
        return Tool.objectToJson(zjds);
    }

    /**
     * 根据行政代码 查找 符合的宅基地
     * @return
     */
    @PostMapping("/findbydjzqdm")
    @ResponseBody
    public ResultData  findByDJZQDM(String djzqdmsStr) {
        List<String> djzqdms = Tool.jsonToObject(djzqdmsStr,new TypeToken<List<String>>(){}.getType());
        List<ZJD> zjds =  zjdService.findByDJZQDM(djzqdms);
        ResultData resultData = resultDataService.getSuccessResultData(zjds);
        return resultData;
    }

    /**
     * 根据 ZDNUM 查询 zjd
     * @return
     */
    @RequestMapping(value = "/findbyzdnum")
    public String  findAll(String ZDNUM) {
        if(Tool.isEmpty(ZDNUM)){
            return  "";
        }
        ZJD zjd =  zjdService.findByZDNUM(ZDNUM);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(zjd);
    }

    /**
     * 根据 ZDNUM 查询 zjd
     * @return
     */
    @RequestMapping(value = "/findbyzdnumresultdata")
    public ResultData  findbyzdnum(String ZDNUM) {
        if(Tool.isEmpty(ZDNUM)){
            return  resultDataService.getErrorResultData("没有传输编码过来");
        }
        ZJD zjd =  zjdService.findByZDNUM(ZDNUM);

        return  resultDataService.getSuccessResultData(zjd);
    }


    /**
     * 保存宅基地
     * @param zjds
     */
    @PostMapping("/savezjds")
    @ResponseBody
    public ResultData saveZJDs(String zjds) {
        if(Tool.isEmpty(zjds)){
            return  resultDataService.getOtherResultData("没有需要保存的地块");
        }
        List<ZJD> zjdsList = Tool.jsonToObject(zjds,new TypeToken<List<ZJD>>(){}.getType());
        zjdService.saveAll(zjdsList);
        return  resultDataService.getSuccessResultData("");
    }
    /**
     * 修改宅基地
     * @param zjd
     */
    @PostMapping("/updatezjd")
    @ResponseBody
    public ResultData updateZJD(String zjd) {
        if(Tool.isEmpty(zjd)){
            return  resultDataService.getOtherResultData("没有需要保存的地块");
        }
        ZJD zjdPo = Tool.jsonToObject(zjd,ZJD.class);

        //修改照片路径
        ZJD oldZJD = zjdService.findById(zjdPo.getId());
        photoService.updatePhotoPath(oldZJD,zjdPo);
        zjdService.save(zjdPo);
        return  resultDataService.getSuccessResultData("");
    }
    /**
    * 删除宅基地， 和 所属的宅基地
     * @param po
     */
    @PostMapping("/deletezjd")
    @ResponseBody
    public ResultData deleteZJD(String po) {
        if(Tool.isEmpty(po)){
            return  resultDataService.getErrorResultData("没有需要删除地块");
        }
        ZJD zjdPo = Tool.jsonToObject(po,ZJD.class);
        zjdService.deleteZJD(zjdPo);
        photoService.deleteAllPhotoByZJD(zjdPo);
        return  resultDataService.getSuccessResultData("");
    }

    @RequestMapping(value = "/test")
    public void test(HttpServletRequest request, HttpServletResponse response) {
        List<ZJD> ZJDS =  zjdService.findAll();
        Photo photo =  ZJDS.get(0).getPhotos().get(0);
        photo.setPath("d:/99988877.jpg");
        //photoService.deletePhoto(photo);
        zjdService.delete(ZJDS);
    }

    @PostMapping("/findzjdsbyxzdmanduser")
    @ResponseBody
    public ResultData findzjdsbyxzdm_user(String userid,String djzqdms) {
        Long id =null ;
        if(!Tool.isEmpty(userid)){
            id = Long.parseLong(userid);
        }
        List<String> djzqdmsList = Tool.jsonToObject(djzqdms,new TypeToken<List<String>>(){}.getType());
        List<ZJD> zjds =  zjdService.findByDJZQDM(id,djzqdmsList);

        return  resultDataService.getSuccessResultData(zjds);
    }
}
