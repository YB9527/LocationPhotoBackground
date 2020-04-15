package com.xupu.zjd.po;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ZJD {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Expose
    private String mDKBM;
    @Column(nullable = false)
    @Expose
    private String mDKMC;

    @OneToMany(mappedBy = "zjd",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @Expose
    private List<Photo> photos;

    @Expose
    @OneToMany(mappedBy = "zjdGeometry",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private  List<ZJDGeometry> zjdGeometry;//考虑多部件情况


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
    public ZJD(String mDKBM, String mDKMC) {
        this.mDKBM = mDKBM;
        this.mDKMC = mDKMC;
    }

    public List<Photo> getPhotos() {
        if(photos == null){
            photos = new ArrayList<>();
        }
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getmDKBM() {
        return mDKBM;
    }

    public String getmDKMC() {
        return mDKMC;
    }

    public void setmDKBM(String mDKBM) {
        this.mDKBM = mDKBM;
    }

    public void setmDKMC(String mDKMC) {
        this.mDKMC = mDKMC;
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
                "mDKBM='" + mDKBM + '\'' +
                ", mDKMC='" + mDKMC + '\'' +
                ", id=" + id +
                '}';
    }

}
