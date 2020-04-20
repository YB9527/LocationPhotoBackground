package com.xupu.xzqy.po;

import com.google.gson.annotations.Expose;
import com.xupu.zjd.po.ZJD;


import javax.persistence.*;
import java.util.List;

/**
 * 行政区域
 */
@Entity
public class XZDM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;
    @Expose
    private String DJZQDM;
    @Expose
    private String DJZQMC;
    /**
     * 不参与数据的映射
     */
    @Transient
    private List<ZJD> zjds;

    public XZDM(){

    }

    @Override
    public String toString() {
        return "XZDM{" +
                "id=" + id +
                ", DJZQDM='" + DJZQDM + '\'' +
                ", DJZQMC='" + DJZQMC + '\'' +
                '}';
    }

    public List<ZJD> getZjds() {
        return zjds;
    }

    public void setZjds(List<ZJD> zjds) {
        this.zjds = zjds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDJZQDM() {
        return DJZQDM;
    }

    public void setDJZQDM(String DJZQDM) {
        this.DJZQDM = DJZQDM;
    }

    public String getDJZQMC() {
        return DJZQMC;
    }

    public void setDJZQMC(String DJZQMC) {
        this.DJZQMC = DJZQMC;
    }
}