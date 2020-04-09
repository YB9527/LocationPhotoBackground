package com.xupu.zjd.po;

import org.springframework.web.multipart.MultipartFile;

public class PersonCustom {
    // 参数
    private String ss;
    // 文件
    private MultipartFile file;


    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    // 省略get\set
}
