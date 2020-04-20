package com.xupu.common.po;

import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public class UploadFileHelper {

    // 文件
    private MultipartFile file;



    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
