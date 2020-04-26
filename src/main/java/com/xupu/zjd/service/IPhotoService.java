package com.xupu.zjd.service;

import com.xupu.common.tools.SpringBootTool;
import com.xupu.zjd.po.Photo;
import com.xupu.zjd.po.ZJD;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IPhotoService {
    /**
     * 存照片的根路径
     */
    public static String dkPhotoDir = SpringBootTool.getRootDir() + "zjd/zjdphoto/";
    public String getPhotoNativePath(Photo photo,ZJD zjd);
    void deletePhoto(ZJD zjd,Photo photo);

    /**
     * 保存文件到本地
     * @param file
     * @param photo
     * @return
     */
    boolean savePhotoFile(MultipartFile file, Photo photo, ZJD zjd);

    /**
     * 保存photo 并修改文件名字
     * @param zjd 要用地块编码改本地文件名字
     * @param photo
     */
    void savePhoto(ZJD zjd,Photo photo);


    /**
     * 保存所有文件， 并创建所有Photo对象
     * @param map
     */
    List<ZJD>  savePhotoFile(Map<String, MultipartFile> map);

    /**
     * 删除 宅基地照片
     * @param zjdPo
     */
    void deleteAllPhotoByZJD(ZJD zjdPo);

    /**
     * 修改 宅基地照片路径
     * @param oldZJD
     * @param newZJD
     */
    void updatePhotoPath(ZJD oldZJD, ZJD newZJD);
}
