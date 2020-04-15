package com.xupu.zjd.po;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
public class ZJDGeometry {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Expose
    private String dkbm;
    @Expose
    private String geometry;
    @Expose
    private String geometryStyle;
    @Expose
    private String geometryType;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    private  ZJDGeometry zjdGeometry;

    public ZJDGeometry getZjdGeometry() {
        return zjdGeometry;
    }

    public void setZjdGeometry(ZJDGeometry zjdGeometry) {
        this.zjdGeometry = zjdGeometry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDkbm() {
        return dkbm;
    }

    public String getGeometryStyle() {
        return geometryStyle;
    }

    public void setGeometryStyle(String geometryStyle) {
        this.geometryStyle = geometryStyle;
    }

    public String getGeometryType() {
        return geometryType;
    }

    public void setGeometryType(String geometryType) {
        this.geometryType = geometryType;
    }

    public void setDkbm(String dkbm) {
        this.dkbm = dkbm;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }
}
