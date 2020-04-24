package com.xupu.zjd.po;

import com.google.gson.annotations.Expose;
import com.xupu.usermanager.po.User;
import com.xupu.xzqy.po.XZDM;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 宅基地
 */
@Entity
public class ZJD {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Expose
    private String ZDNUM;
    @Column(nullable = false)
    @Expose
    private String QUANLI;
    @Expose
    private String bz;
    /**
     * 地块是否已经上传,服务器里面的都是true,因为已经存在了
     */
    @Expose
    private Boolean isUpload;





    @OneToMany(mappedBy = "zjd", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Expose
    private List<Photo> photos;

    @Expose
    @OneToMany(mappedBy = "zjd", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ZJDGeometry> zjdGeometry;//考虑多部件情况

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = true)
    @Expose
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = true)
    @Expose
    private XZDM xzdm;
    @Expose
    private String DJZQDM;

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getDJZQDM() {
        return DJZQDM;
    }
    public Boolean getUpload() {
        return true;
    }

    public void setUpload(Boolean upload) {
        isUpload = true;
    }
    public void setDJZQDM(String DJZQDM) {
        this.DJZQDM = DJZQDM;
    }

    public List<ZJDGeometry> getZjdGeometry() {
        return zjdGeometry;
    }

    public void setZjdGeometry(List<ZJDGeometry> zjdGeometry) {
        this.zjdGeometry = zjdGeometry;
    }

    public ZJD() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZJD(String ZDNUM, String QUANLI) {
        this.ZDNUM = ZDNUM;
        this.QUANLI = QUANLI;
    }

    public XZDM getXzdm() {
        return xzdm;
    }

    public void setXzdm(XZDM xzdm) {
        this.xzdm = xzdm;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Photo> getPhotos() {
        if (photos == null) {
            photos = new ArrayList<>();
        }
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
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

   /* public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }*/

    @Override
    public String toString() {
        return "ZJD{" +
                "ZDNUM='" + ZDNUM + '\'' +
                ", QUANLI='" + QUANLI + '\'' +
                ", id=" + id +
                '}';
    }

}
