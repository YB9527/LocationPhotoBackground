package com.xupu.project.controller;

import com.xupu.project.po.Media;
import com.xupu.common.po.ResultData;
import com.xupu.project.service.IMediaService;
import com.xupu.common.service.ResultDataService;
import com.xupu.common.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/media")
public class MediaController {
    @Autowired
    private IMediaService mediaService;
    private ResultDataService resultDataService = ResultDataService.getResultDataService();

    @RequestMapping(value = "/findmediatypes")
    public ResultData findmediatypes() {
        //String json = Tool.objectToJson(mediaService.findMediaTypes());

        return resultDataService.getSuccessResultData(mediaService.findMediaTypes());
    }
    @RequestMapping(value = "/findmedias")
    public ResultData findMedias(Long projectid) {
        //String json = Tool.objectToJson(mediaService.findMediaTypes());
        if(projectid == null){
            return  resultDataService.getErrorResultData("没有传项目id过来！！！");
        }
        return resultDataService.getSuccessResultData(mediaService.findMedias(projectid));
    }


    @PostMapping("/addmedia")
    @ResponseBody
    public ResultData addMedia(Long projectid,String media) {
        if(projectid == null){
            return  resultDataService.getErrorResultData("没有传projectid过来");
        }
        Media mediaPo = Tool.jsonToObject(media,Media.class);
        return mediaService.addMedia(projectid,mediaPo);
    }

    @RequestMapping(value = "/deletebyid")
    public ResultData deleteById(Long mediaid) {
        //String json = Tool.objectToJson(mediaService.findMediaTypes());
        if(mediaid == null){
            return  resultDataService.getErrorResultData("没有传项目id过来！！！");
        }
        mediaService.deleteById(mediaid);
        return resultDataService.getSuccessMessageResultData("删除成功");
    }


    @PostMapping("/updatemedia")
    @ResponseBody
    public ResultData upDateMedia(String media) {
        Media mediaPo = Tool.jsonToObject(media,Media.class);
        return mediaService.upDateMedia(mediaPo);
    }
}
