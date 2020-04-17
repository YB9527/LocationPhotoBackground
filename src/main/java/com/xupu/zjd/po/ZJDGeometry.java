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
    private String ZDNUM;
    @Expose
    private String QUANLI;

    /**
     * arcgis 中长度，只有 线、面 图形有
     */
    @Expose
    private String SHAPE_Length;
    /**
     * arcgis 中面积，只有 面 图形有
     */
    @Expose
    private String SHAPE_Area;
    @Expose
    private String geometry;
    @Expose
    private String BZ;
    @Expose
    private String geometryStyle;
    @Expose
    private String geometryType;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    private  ZJD zjd;




    public String getSHAPE_Length() {
        return SHAPE_Length;
    }

    public void setSHAPE_Length(String SHAPE_Length) {
        this.SHAPE_Length = SHAPE_Length;
    }

    public String getSHAPE_Area() {
        return SHAPE_Area;
    }

    public void setSHAPE_Area(String SHAPE_Area) {
        this.SHAPE_Area = SHAPE_Area;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }

    public ZJD getZjd() {
        return zjd;
    }

    public void setZjd(ZJD zjd) {
        this.zjd = zjd;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZDNUM() {
        return ZDNUM;
    }

    public void setZDNUM(String ZDNUM) {
        this.ZDNUM = ZDNUM;
    }

    public String getQUANLI() {
        return QUANLI;
    }

    public void setQUANLI(String QUANLI) {
        this.QUANLI = QUANLI;
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



    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }
}
