package com.xupu.zjd.service;

import com.xupu.common.YBException.ZJDException;
import com.xupu.common.tools.FileTool;
import com.xupu.common.tools.ReflectTool;
import com.xupu.zjd.dao.PhotoRepository;
import com.xupu.zjd.po.Photo;
import com.xupu.zjd.po.ZJD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

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
        return FileTool.savePhotoFile(file, upload);
    }



    @Override
    public void savePhoto(ZJD zjd, Photo photo) {
        String srcPath = getPhotoNativePath(photo, zjd);
        Photo oldPhoto = photoRepository.findById(photo.getId()).get();

        String nativePath = getPhotoNativePath(oldPhoto, zjd);
        if(photo.getId() != null){
            //替换属性
            String path =FileTool.getDir(photo.getPath())+"/"+photo.getName();

            String bz = photo.getBz();
            oldPhoto.setPath(path);
            oldPhoto.setBz(bz);
            photo = oldPhoto;
        }
        if (!srcPath.equals(nativePath)) {
            File file = new File(nativePath);
            if (file.exists()) {
                file.renameTo(new File(srcPath));
               // photo.setPath(srcPath);
            }
        }
        photo.setZjd(zjd);

        photoRepository.save(photo);
    }


    @Override
    public List<ZJD> savePhotoFile(Map<String, MultipartFile> map) {
        List<ZJD> zjds = zjdService.findAll();
        Map<String, ZJD> zjdMap = null;
        try {
            zjdMap = ReflectTool.getIDMap("getZDNUM", zjds);
        } catch (ZJDException e) {
            e.printStackTrace();
        }
        for (String androidPath : map.keySet()
        ) {
            MultipartFile file = map.get(androidPath);
            Photo photo = new Photo(androidPath, true);
            String dkbm = getDKBMByAndroidPath(androidPath);
            String fileName = getFileNameyAndroidPath(androidPath);
            String path = dkPhotoDir + dkbm + "/" + fileName;
            File upload = new File(path);
            ZJD zjd = zjdMap.get(dkbm);
            zjd.getPhotos().add(photo);
            photo.setZjd(zjd);
            FileTool. savePhotoFile(file, upload);
        }
        zjdService.saveOrUpdateAll(zjds);
        return zjds;
    }

    public String getNativePath(String androidPath){
        String dkbm = getDKBMByAndroidPath(androidPath);
        String fileName = getFileNameyAndroidPath(androidPath);
        String path = dkPhotoDir + dkbm + "/" + fileName;
        return  path;
    }



    /**
     * 通过安卓保存的路径 得到 文件名字
     *
     * @param androidPath
     * @return
     */
    public String getFileNameyAndroidPath(String androidPath) {
        String flag = "com.xp/files";
        String name = androidPath.substring(androidPath.lastIndexOf("/") + 1);
        return name;
    }

    @Override
    //通过安卓保存的路径 得到 宅基地编码  /storage/emulated/0/Android/data/com.xp/cache/zjd/zjdphoto/2/临时照片3333.jpg
    public String getDKBMByAndroidPath(String androidPath) {
        String flag = "zjd/zjdphoto/";
        int start = androidPath.indexOf(flag) + flag.length();
        int end = androidPath.indexOf("/", start);
        String dkbm = androidPath.substring(start, end);
        return dkbm;
    }

    /**
     * 删除 照片
     *
     * @param zjdPo
     */
    @Override
    public void deleteAllPhotoByZJD(ZJD zjdPo) {
        if (zjdPo != null) {
            for (Photo photo : zjdPo.getPhotos()) {
                deletePhoto(zjdPo, photo);
            }
        }
    }

    @Override
    public void updatePhotoPath(ZJD oldZJD, ZJD newZJD) {
        if(oldZJD == null || newZJD == null){
            return;
        }
        File file1 = new File( dkPhotoDir + oldZJD.getZDNUM());
        //将原文件夹更改为A，其中路径是必要的。注意
        file1.renameTo(new File(dkPhotoDir + newZJD.getZDNUM()));

        //修改照片路径
        for (Photo photo :newZJD.getPhotos()){
            photo.setPath(photo.getPath().replace(dkPhotoDir + oldZJD.getZDNUM(),dkPhotoDir + newZJD.getZDNUM()));
        }

    }
    @Override
    public void deletePhoto(Long id) {
        photoRepository.deleteById(id);
    }

}
