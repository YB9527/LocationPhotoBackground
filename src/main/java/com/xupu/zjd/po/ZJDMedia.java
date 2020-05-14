package com.xupu.zjd.po;

import com.google.gson.annotations.Expose;
import com.xupu.common.tools.FileTool;
import com.xupu.project.po.Media;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "o_zjdmedia")
public class ZJDMedia extends Media {

    @Expose
    private String path;
    @Expose
    private String fileName;
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

    @Expose
    @ManyToOne(cascade = {CascadeType.REFRESH}, optional = false)
    private ZJD zjd;


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





    public void setPath(String path) {
        this.path = path;
        setName(FileTool.getFileName(path));
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ZJD getZjd() {
        return zjd;
    }

    public void setZjd(ZJD zjd) {
        this.zjd = zjd;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ZJDMedia zjdMedia = (ZJDMedia) o;
        return Objects.equals(path, zjdMedia.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), path);
    }
}
