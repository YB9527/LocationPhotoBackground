package com.xupu.cbd.po;

import com.xupu.common.FileTool;

import javax.persistence.*;


public class Photo {

    private Long id;

    private String path;

    private String name;

    private String describe;


    public Photo() {
    }

    public Photo(String path, String name, String describe) {
        this.path = path;
        this.name = name;
        this.describe = describe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPath(String path) {

        this.path = path;
        //setName(FileTool.getFileName(path));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getDescribe() {
        return describe;
    }

}
