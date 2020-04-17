package com.xupu.xzqy.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xupu.common.Tool;
import com.xupu.common.po.ResultData;
import com.xupu.common.po.Status;
import com.xupu.xzqy.po.XZDM;
import com.xupu.xzqy.service.IXZDMService;
import com.xupu.zjd.po.ZJD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/xzdm")
public class XZDMController{
    @Autowired
    IXZDMService xzdmService;

    /**
     * 查询所有 行政代码
     * @return
     */
    @RequestMapping(value = "/findall")
    public ResultData  findAll() {
        List<XZDM> xzdms =  xzdmService.findAll();
        return  new ResultData(Status.Success,"成功",Tool.getGson().toJson(xzdms));
    }



}
