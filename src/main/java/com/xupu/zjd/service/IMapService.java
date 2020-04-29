package com.xupu.zjd.service;


import com.xupu.common.po.ResultData;
import com.xupu.common.tools.SpringBootTool;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IMapService {
    public static  String shpDir = SpringBootTool.getRootDir() + "zjd/shp/";

    ResultData readShp(Map<String, MultipartFile> map);
}
