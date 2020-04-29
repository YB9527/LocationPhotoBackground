package com.xupu.zjd.service;

import com.xupu.common.po.ResultData;
import com.xupu.common.service.ResultDataService;
import com.xupu.common.tools.FileTool;
import com.xupu.common.tools.FormatShp;
import com.xupu.common.tools.SpringBootTool;
import com.xupu.common.tools.Tool;
import com.xupu.zjd.po.ZJD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class MapService implements IMapService {
    @Autowired
    private  IZJDService zjdService;

    private ResultDataService  resultDataService = ResultDataService.getResultDataService();

    @Override
    public ResultData readShp(Map<String, MultipartFile> map) {
        String path="";
        String dirStr = SpringBootTool.getRootDir()+"shp/";

        boolean bl = FileTool.delAllFile(dirStr);
        File dir = new File(dirStr);
        dir.mkdirs();
        for (String fileName : map.keySet()) {
            //如果文件夹存在，就删除
            path = dirStr+fileName;
            FileTool.savePhotoFile(map.get(fileName),new File(path));
        }
        File file = new File( path.substring(1,path.lastIndexOf("."))+".shp");
        path = file.getAbsolutePath();
        List<String> featureJsons = FormatShp.shp2json(path);
        if(Tool.isEmpty(featureJsons)){
            return  resultDataService.getErrorResultData("没有数据可以转换");
        }
        List<ZJD> zjds =zjdService.jsonToZJD(featureJsons);
        List<String> errs = zjdService.checkZJDs(zjds);
        if(!Tool.isEmpty(errs)){
            return  resultDataService.getErrorResultData(Tool.listToString(errs,","));
        }
        return  resultDataService.getSuccessResultData(zjds);
    }
}
