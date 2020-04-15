package com.xupu.zjd.controller;


import com.google.gson.Gson;
import com.xupu.common.FileTool;
import com.xupu.common.SpringBootTool;
import com.xupu.common.Tool;
import com.xupu.common.UploadFileHelper;
import com.xupu.zjd.po.*;
import com.xupu.zjd.service.IPhotoService;
import com.xupu.zjd.service.IZJDService;
import com.xupu.zjd.service.PhotoService;
import com.xupu.zjd.service.ZJDService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jack on 2017/10/30.
 */
@RestController
@RequestMapping(value = "/zjdphoto")
public class PhotoController {

    @Autowired
    private IPhotoService photoService;
    @Autowired
    private IZJDService zjdService;

    /**
     * 删除照片
     * @param photo 被删除的照片
     * @param zjd  zjd中的 照片
     * @return
     */
    @PostMapping("/deletePhoto")
    @ResponseBody
    public boolean deletePhoto(String photo, String zjd) {

        Photo photoPo = Tool.getGson().fromJson(photo, Photo.class);
        ZJD zjdPo = zjdService.findById(Tool.getGson().fromJson(zjd, ZJD.class).getId());
        photoService.deletePhoto(zjdPo, photoPo);
        return true;
    }

    /**
     * 修改照片信息
     * @param photo
     * @param zjd
     * @return
     */
    @PostMapping("/updatephoto")
    @ResponseBody
    public boolean updatePhoto(String photo, String zjd) {
        Photo photoPo = Tool.getGson().fromJson(photo, Photo.class);
        ZJD zjdPo = zjdService.findById(Tool.getGson().fromJson(zjd, ZJD.class).getId());

         photoService.savePhoto(zjdPo, photoPo);
        return true;
    }


    /**
     * 多个照片上传
     * @param uploadFiles
     * @param request
     * @return
     */
    @RequestMapping("/uploads")
    public String upload(MultipartFile[] uploadFiles, HttpServletRequest request) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        // 获取文件
        Map<String,MultipartFile> map =  params.getFileMap();
        List<ZJD> zjds = photoService.savePhotoFile(map);
        return Tool.getGson().toJson(zjds);
    }


    @RequestMapping(value = "/zjdphotodowload")
    public void downloadFile(@RequestParam(name = "dkbm") String dkbm, @RequestParam(name = "photoname") String photoname, HttpServletRequest request, HttpServletResponse response) throws IOException {


        InputStream f = new FileInputStream(photoService.dkPhotoDir + dkbm + "/" + photoname);


        response.reset();
        //response.setContentType("application/x-msdownload;charset=utf-8");
        response.setContentType("image/png");

        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(("timg" + ".png").getBytes("gbk"), "iso-8859-1"));//下载文件的名称
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ServletOutputStream sout = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(f);
            bos = new BufferedOutputStream(sout);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bos.flush();
            bos.close();
            bis.close();
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
    }

    @PostMapping("/demo1")
    @ResponseBody
    public String handleFileUpload1(Photo photo) {
// 文件和参数 都在实体中了
        return "";
    }

    @PostMapping(value = "/upload")
    @ResponseBody
    public String handleFileUpload(UploadPhotoHelper uploadHelper) {
// 文件和参数 都在实体中了
        Photo photo = new Gson().fromJson(uploadHelper.getPhoto(), Photo.class);
        ZJD zjd = new Gson().fromJson(uploadHelper.getZjd(), ZJD.class);
        boolean isSuccess = photoService.savePhotoFile(uploadHelper.getFile(), photo, zjd);
        if (isSuccess) {
            zjd = zjdService.findById(zjd.getId());
            zjd.getPhotos().add(photo);
            photo.setUpload(true);
            photo.setZjd(zjd);
            zjdService.save(zjd);
        } else {
            return "1";
        }
        return "0";
    }

    /**
     * 上传照片
     * @param srcFile
     * @param photo
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/upload11")
    public String fileUpload(@RequestParam("file") MultipartFile srcFile, @RequestBody Photo photo, RedirectAttributes redirectAttributes) {
        //前端没有选择文件，srcFile为空

        //选择了文件，开始上传操作
        /*try {

            File upload = new File(photoService.dkPhotoDir + photo.getZjd().getmDKBM() + "/" + photo.getName());
            if (upload.exists()) {
                upload.delete();//如果已经存在，删除文件
            }
            FileTool.exitsDir(upload.getParent(), true);

            //根据srcFile大小，准备一个字节数组
            byte[] bytes = srcFile.getBytes();
            //拼接上传路径
            //Path path = Paths.get(UPLOAD_FOLDER + srcFile.getOriginalFilename());
            //通过项目路径，拼接上传路径
            Path path = Paths.get(upload.getAbsolutePath());
            //** 开始将源文件写入目标地址
            Files.write(path, bytes);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            // 获得文件原始名称
            String fileName = srcFile.getOriginalFilename();
            // 获得文件后缀名称
            String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            // 生成最新的uuid文件名称
            String newFileName = uuid + "." + suffixName;
            redirectAttributes.addFlashAttribute("message", "文件上传成功" + newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return "redirect:upload_status";
    }


    @RequestMapping(value = "pic")
    public void queryPic(@RequestParam(required = false) String adress, HttpServletRequest request, HttpServletResponse response) throws IOException {


        response.setContentType("image/png");
        FileInputStream is = new FileInputStream("./img/a.png");

        if (is != null) {
            int i = is.available(); // 得到文件大小
            byte data[] = new byte[i];
            is.read(data); // 读数据
            is.close();
            response.setContentType("image/png");  // 设置返回的文件类型
            OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
            toClient.write(data); // 输出数据
            toClient.close();
        }

    }


}


