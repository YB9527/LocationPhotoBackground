package com.xupu.common.tools;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 文件处理工具类
 */
public class FileTool {
    /**
     * @param path
     * @return 获取文件的名字 包含文件后缀名
     */
    public static String getFileName(String path) {
        if (path == null) {
            return null;
        }
        int index = path.lastIndexOf("/");
        if (index != -1 && index != path.length() - 1) {
            return path.substring(index + 1);
        }
        return null;
       /* Path.GetFileNameWithoutExtension
        Path.GetExtension
        Path.GetFileName*/
    }

    /***
     *
     * @param path
     * @return 返回不具有后缀名的 文件名
     */
    public static String getFileNameWithoutExtension(String path) {
        String fileName = getFileName(path);
        if (fileName == null) {
            return null;
        }
        int index = fileName.indexOf(".");
        if (index != -1) {
            return fileName.substring(0, index);
        }
        return null;
    }

    /**
     *
     * @param path
     * @return 返回后缀名
     */
    public static String getExtension(String path) {
        String fileName = getFileName(path);
        if (fileName == null) {
            return null;
        }
        int index = fileName.indexOf(".");
        if (index != -1 && index != fileName.length() - 1) {
            return fileName.substring(index + 1);
        }
        return null;
    }

    /**
     *
     * @param path
     * @return 获取文件路径
     */
    public static String getDir(String path) {
        if (path == null) {
            return null;
        }
        int index = path.lastIndexOf("/");
        if (index >0 ) {
            return path.substring(0,index);
        }
        return null;
    }
    /**
     * 文件复制
     * @param source
     * @param dest
     * @return
     */
    public static boolean copyFile(String source, String dest) {
        FileChannel input = null;
        FileChannel output = null;
        try {
            File dir = new File(getDir(dest));
            if(!dir.exists()){
                dir.mkdirs();
            }
            input = new FileInputStream(new File(source)).getChannel();
            output = new FileOutputStream(new File(dest)).getChannel();
            output.transferFrom(input, 0, input.size());
        } catch (Exception e) {
            return false;
        }
        return  true;
    }

    /**
     * 检查文件是否存在
     * @param filePath
     * @return
     */
    public static boolean exitFile(String filePath) {
        boolean bl = (filePath == null|| filePath.trim() == "")?false:new File(filePath).exists();
        return  bl;

    }

    /**
     * 删除文件
     * @param path
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        if(file.exists()){
            return file.delete();
        }
        return  false;
    }
    /**
     * 检查文件夹是否存在
     *
     * @param dir
     * @param isCreated
     * @return
     */
    public static boolean exitsDir(File dir, boolean isCreated) {
        if (dir.exists()) {
            return true;
        } else {
            if (isCreated) {
                return dir.mkdirs();
            }
        }
        return false;
    }
    /**
     * 检查文件夹是否存在
     *
     * @param dir
     * @param isCreated
     * @return
     */
    public static boolean exitsDir(String dir, boolean isCreated) {
        File  f = new File(dir);
        return  exitsDir(f,isCreated);
    }

    /**
     *下载文件
     * @param response
     * @param filePath 文件路径
     */
    public static void DownLoadFile(HttpServletResponse response, String filePath)  {

        response.reset();
        //response.setContentType("application/x-msdownload;charset=utf-8");
        //response.setContentType("image/png");
        try {
            File file = new File(filePath);
            if(!file.exists()){
                return;
            }
            InputStream f = new FileInputStream(filePath);
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((FileTool.getFileName(filePath)).getBytes("gbk"), "iso-8859-1"));//下载文件的名称
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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (IOException e2) {
            e2.printStackTrace();
        }

    }
    /**
     * @param file   数据源
     * @param upload 新建空数据库
     * @return
     */

    public static Boolean savePhotoFile(MultipartFile file, File upload) {
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


    /**
     * 删除 所有的文件
     * @param path
     * @return 删除成功 或者 没有文件夹返回 true
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
