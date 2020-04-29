package com.xupu.zjd.controller;

import com.xupu.common.po.ResultData;
import com.xupu.zjd.service.IMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(value = "/map")
public class MapController {
    @Autowired
    private IMapService mapService;
    /**
     * 多个上传
     * @param uploadFiles
     * @param request
     * @return
     */
    @PostMapping(value = "/uploads")
    @ResponseBody
    public ResultData upload(MultipartFile[] uploadFiles, HttpServletRequest request) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        // 获取文件
        Map<String,MultipartFile> fileMap =  params.getFileMap();

        return   mapService.readShp(fileMap);
    }

    @RequestMapping(value = "/test")
    public String test(HttpServletRequest request, HttpServletResponse response) {
        String aa ="123";
        return  aa;
       // MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        // 获取文件
        //Map<String,MultipartFile> map =  params.getFileMap();
    }

}
