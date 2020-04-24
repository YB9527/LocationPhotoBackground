package com.xupu.zjd.service;

import com.xupu.common.tools.FileTool;
import com.xupu.common.tools.ReflectTool;
import com.xupu.zjd.dao.PhotoRepository;
import com.xupu.zjd.po.Photo;
import com.xupu.zjd.po.ZJD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PhotoService implements IPhotoService {

    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private IZJDService zjdService;

    @Override
    public String getPhotoNativePath(Photo photo, ZJD zjd) {

        return dkPhotoDir + zjd.getZDNUM() + "/" + photo.getName();
    }

    @Override
    public void deletePhoto(ZJD zjd, Photo photo) {

        photoRepository.delete(photo);
        //删除本地文件
        String nativePath = getPhotoNativePath(photo, zjd);
        File file = new File(nativePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 保存文件
     *
     * @param file
     * @param photo
     */
    @Override
    public boolean savePhotoFile(MultipartFile file, Photo photo, ZJD zjd) {
//前端没有选择文件，srcFile为空
        //选择了文件，开始上传操作

        File upload = new File(dkPhotoDir + zjd.getZDNUM() + "/" + photo.getName());
        return savePhotoFile(file, upload);
    }

    /**
     *
     * @param file 数据源
     * @param upload 新建空数据库
     * @return
     */

    private Boolean savePhotoFile(MultipartFile file, File upload) {
        try {
            if (upload.exists()) {
                upload.delete();//如果已经存在，删除文件
            }
            FileTool.exitsDir(upload.getParent(), true);

            //根据srcFile大小，准备一个字节数组
            byte[] bytes = file.getBytes();
            //拼接上传路径
            //Path path = Paths.get(UPLOAD_FOLDER + srcFile.getOriginalFilename());
            //通过项目路径，拼接上传路径
            Path path = Paths.get(upload.getAbsolutePath());
            //** 开始将源文件写入目标地址
            Files.write(path, bytes);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            // 获得文件原始名称
            String fileName = file.getOriginalFilename();
            // 获得文件后缀名称
            String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            // 生成最新的uuid文件名称
            String newFileName = uuid + "." + suffixName;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void savePhoto(ZJD zjd, Photo photo) {
        String srcPath = getPhotoNativePath(photo, zjd);
        String nativePath = getPhotoNativePath(photoRepository.findById(photo.getId()).get(), zjd);

        if (!srcPath.equals(nativePath)) {
            File file = new File(nativePath);
            if (file.exists()) {
                file.renameTo(new File(srcPath));
            }
        }
        photo.setZjd(zjd);
        photoRepository.saveAndFlush(photo);
    }


    @Override
    public  List<ZJD> savePhotoFile( Map<String, MultipartFile> map) {
        List<ZJD> zjds = zjdService.findAll();
        Map<String,ZJD> zjdMap = ReflectTool.getIDMap("getZDNUM",zjds) ;
        for (String androidPath:map.keySet()
             ) {
            MultipartFile file = map.get(androidPath);
            Photo photo = new Photo(androidPath,true);
            String dkbm =getDKBMByAndroidPath(androidPath);
            String fileName = getFileNameyAndroidPath(androidPath);
            String path = dkPhotoDir + dkbm + "/" + fileName;
            File upload = new File(path);
            ZJD zjd = zjdMap.get(dkbm);
            zjd.getPhotos().add(photo);
            photo.setZjd(zjd);
            savePhotoFile(file,upload);
        }
        zjdService.saveAll(zjds);
        return zjds;
    }

    /**
     * 通过安卓保存的路径 得到 文件名字
     * @param androidPath
     * @return
     */
    private String getFileNameyAndroidPath(String androidPath) {
        String flag = "com.xp/files";
        String name = androidPath.substring(androidPath.lastIndexOf("/")+1);
        return  name;
    }
    //通过安卓保存的路径 得到 宅基地编码  /storage/emulated/0/Android/data/com.xp/cache/zjd/zjdphoto/2/临时照片3333.jpg
    private String getDKBMByAndroidPath(String androidPath) {
        String flag = "zjd/zjdphoto/";
        int start = androidPath.indexOf(flag)+flag.length();
        int end = androidPath.indexOf("/",start);
        String dkbm = androidPath.substring(start,end);
        return dkbm;
    }


}
