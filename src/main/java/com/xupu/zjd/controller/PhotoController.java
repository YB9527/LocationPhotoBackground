package com.xupu.zjd.controller;


import com.xupu.common.po.ResultData;
import com.xupu.common.po.Status;
import com.xupu.common.service.ResultDataService;
import com.xupu.common.tools.Tool;
import com.xupu.zjd.po.Photo;
import com.xupu.zjd.po.UploadPhotoHelper;
import com.xupu.zjd.po.ZJD;
import com.xupu.zjd.service.IPhotoService;
import com.xupu.zjd.service.IZJDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by jack on 2017/10/30.
 */
@RestController
@RequestMapping(value = "/zjdphoto")
public class PhotoController {

    //private static CommonMethod cm = new CommonMethod();
    private static ResultDataService resultDataService = ResultDataService.getResultDataService();
    @Autowired
    private IPhotoService photoService;
    @Autowired
    private IZJDService zjdService;

    /**
     * 删除照片
     *
     * @param photo 被删除的照片
     * @param zjd   zjd中的 照片
     * @return
     */
    @PostMapping("/deletePhoto")
    @ResponseBody
    public boolean deletePhoto(String photo, String zjd) {

        Photo photoPo = Tool.getGson().fromJson(photo, Photo.class);
        ZJD zjdPo = zjdService.findById(Tool.getGson().fromJson(zjd, ZJD.class).getId());
        if (photoPo == null || zjdPo == null) {
            return true;
        }
        photoService.deletePhoto(zjdPo, photoPo);
        return true;
    }

    /**
     * 删除照片
     *
     * @param id 被删除的照片 的 id
     * @return
     */
    @RequestMapping("/deletebyid")
    public ResultData deletebyid(String id) {
        if (Tool.isEmpty(id)) {
            return resultDataService.getErrorResultData("传入的id参数为空");
        }
        Long numid;
        try {
            numid = Long.parseLong(id);
        } catch (Exception e) {
            return resultDataService.getErrorResultData("传入的id参数不是数字,id="+id);
        }
        photoService.deletePhoto(numid);
        return resultDataService.getSuccessResultData("");
    }

    /**
     * 修改照片信息
     *
     * @param photo
     * @param zjd
     * @return
     */
    @PostMapping("/updatephoto")
    @ResponseBody
    public boolean updatePhoto(String photo, String zjd) {
        ResultData resultData =  updatePhototoResultdata(photo,zjd);
        if(resultData.getStatus() == Status.Success){
            return  true;
        }else{
            return  false;
        }
    }
    /**
     * 修改照片信息
     *
     * @param photo
     * @param zjd
     * @return 最新的zjd
     */
    @PostMapping("/updatePhototoResultdata")
    @ResponseBody
    public ResultData updatePhototoResultdata(String photo, String zjd) {
        Photo photoPo = Tool.getGson().fromJson(photo, Photo.class);
        //如果照片已经有了id，那么 检查照片名字是否被更改，如果更改了 检查是否已经存在了

        ZJD zjdPo = zjdService.findById(Tool.getGson().fromJson(zjd, ZJD.class).getId());


        for(Photo p : zjdPo.getPhotos()){
            if(p.getName().equals(photoPo.getName())){
                if(photoPo.getId() == null || photoPo.getId().longValue() != p.getId().longValue() ){
                    //如果是新的照片，或则 id 不同，如果路径相同，就有问题
                    return resultDataService.getErrorResultData("照片路径相同");
                }
            }
        }

        photoService.savePhoto(zjdPo, photoPo);
        //宅基地中的照片替换
        for (int i = 0; i < zjdPo.getPhotos().size(); i++) {
            Photo p = zjdPo.getPhotos().get(i);
            if(p.getId() != null &&photoPo.getId() != null ){
                if(p.getId().longValue() == photoPo.getId().longValue()){
                    zjdPo.getPhotos().set(i,photoPo);
                }
            }
        }
        //zjdService.save(zjdPo);
        return resultDataService.getSuccessResultData(zjdPo);
    }

    /**
     * 多个照片上传
     *
     * @param uploadFiles
     * @param request
     * @return
     */
    @RequestMapping("/uploads")
    public String upload(MultipartFile[] uploadFiles, HttpServletRequest request) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        // 获取文件
        Map<String, MultipartFile> map = params.getFileMap();
        List<ZJD> zjds = photoService.savePhotoFile(map);
        return Tool.getGson().toJson(zjds);
    }


    @RequestMapping(value = "/zjdphotodowload")
    public void downloadFile(@RequestParam(name = "ZDNUM") String ZDNUM, @RequestParam(name = "photoname") String photoname, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = photoService.dkPhotoDir + ZDNUM + "/" + photoname;
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        InputStream f = new FileInputStream(path);
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
        Photo photo = Tool.jsonToObject(uploadHelper.getPhoto(), Photo.class);

        ZJD zjd = Tool.jsonToObject(uploadHelper.getZjd(), ZJD.class);
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
     *
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


    @RequestMapping(value = "lookphoto")
    public void lookphoto(String path, HttpServletResponse response) throws IOException {

        response.setContentType("image/png");

        String nativePath = photoService.getNativePath(path);
        if(!new File(nativePath).exists()){
            return;
        }
        FileInputStream is = new FileInputStream(nativePath);

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


