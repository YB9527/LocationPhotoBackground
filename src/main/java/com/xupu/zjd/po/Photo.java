package com.xupu.zjd.po;

import com.google.gson.annotations.Expose;
import com.xupu.common.FileTool;

import javax.persistence.*;

/**
 * 照片
 */
@Entity
public class Photo {
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
     * 是否已经上传文件
     */
    @Expose
    private Boolean isUpload;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)//可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @JoinColumn(name="zjd_id")//设置在article表中的关联字段(外键)
    private ZJD zjd;

    public Photo(String androidPath, boolean isUpload) {
        setPath(androidPath);
        setUpload(isUpload);

    }

    public Boolean getUpload() {
        return isUpload;
    }

    public void setUpload(Boolean upload) {
        isUpload = upload;
    }



    public Photo() {
    }

    public Photo(String path) {
        setPath(path);
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

    public ZJD getZjd() {
        return zjd;
    }

    public void setZjd(ZJD zjd) {
        this.zjd = zjd;
    }
}
