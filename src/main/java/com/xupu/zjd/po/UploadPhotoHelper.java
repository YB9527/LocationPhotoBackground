package com.xupu.zjd.po;

import com.xupu.common.UploadFileHelper;

public class  UploadPhotoHelper extends UploadFileHelper {
    private String photo;
    private String zjd;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getZjd() {
        return zjd;
    }

    public void setZjd(String zjd) {
        this.zjd = zjd;
    }
}
