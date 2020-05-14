package com.xupu.zjd.po;

import com.google.gson.annotations.Expose;
import com.xupu.common.po.Media;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "o_zjdmedia")
public class ZJDMedia extends Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;
    @Expose
    private String bz;
    /**
     * 必须文件要拥有多少个文件，才能算完成任务
     */
    private Integer mustSize;

    /**
     * 拥有宅基地 文件数量
     */
    private List<ZJDMediaFile> zjdMediaFiles;

    public ZJDMedia(){

    }

    public Integer getMustSize() {
        return mustSize;
    }

    public void setMustSize(Integer mustSize) {
        this.mustSize = mustSize;
    }

    public List<ZJDMediaFile> getZjdMediaFiles() {
        return zjdMediaFiles;
    }

    public void setZjdMediaFiles(List<ZJDMediaFile> zjdMediaFiles) {
        this.zjdMediaFiles = zjdMediaFiles;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }


}
