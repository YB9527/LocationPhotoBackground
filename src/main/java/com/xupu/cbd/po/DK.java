package com.xupu.cbd.po;

import javax.persistence.*;
import java.util.List;

@Entity
public class DK {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mDKBM;

    private String mDKMC;

    //private List<Photo> photos;


    public DK() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public DK(String mDKBM, String mDKMC) {
        this.mDKBM = mDKBM;
        this.mDKMC = mDKMC;
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
        return "DK{" +
                "mDKBM='" + mDKBM + '\'' +
                ", mDKMC='" + mDKMC + '\'' +
                ", id=" + id +
                '}';
    }

}
