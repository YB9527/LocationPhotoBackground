package com.xupu.cbd.po;

import com.xupu.common.FileTool;

public class Photo {
    private String path;
    private String name;
    private String describe;
    private Integer id;
    public Photo(String path, String name, String describe) {
        this.path = path;
        this.name = name;
        this.describe = describe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPath(String path) {

        this.path = path;
        setName(FileTool.getFileName(path));
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
