package com.xupu.zjd.po;

import com.google.gson.annotations.Expose;
import com.xupu.common.tools.FileTool;

import javax.persistence.*;

public class ZJDMediaFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;
    @Expose
    @Column(nullable = false)
    private String path;
    @Expose
    @Column(nullable = false)
    private String name;
    @Expose
    private String bz;
    /**
     * 照片纬度
     */
    @Expose
    private Double latitude;
    /**
     * 照片经度
     */
    @Expose
    private Double longitude;
    /**
     * 拍照时间
     */
    @Expose
    private String createDate;
    /**
     * 是否已经上传文件
     */
    @Expose
    private Boolean isUpload;




    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Boolean getUpload() {
        return isUpload;
    }

    public void setUpload(Boolean upload) {
        isUpload = upload;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
        setName(FileTool.getFileName(path));
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

}
