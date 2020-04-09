package com.xupu.zjd.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xupu.common.Tool;
import com.xupu.zjd.po.ZJD;
import com.xupu.zjd.po.Photo;
import com.xupu.zjd.service.IZJDService;
import com.xupu.zjd.service.IPhotoService;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/zjd")
public class ZJDController {
    @Autowired
    private IZJDService zjdService;
    @Autowired
    private IPhotoService photoService;



    @RequestMapping(value = "/findall")
    public String  findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<ZJD> zjds =  zjdService.findAll();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(zjds);
    }

    @RequestMapping(value = "/savedks")
    public void saveDKs(HttpServletRequest request, HttpServletResponse response) {
        List<ZJD> ZJDS = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ZJD ZJD = new ZJD(i + 1 + "", "小地名");
            /*for (int j = 0; j < 10; j++) {
                Photo photo = new Photo("d:/123.jpg");
                ZJD.getPhotos().add(photo);
                photo.setZJD(ZJD);
            }*/
            ZJDS.add(ZJD);
        }
        zjdService.saveAll(ZJDS);
    }
    @RequestMapping(value = "/test")
    public void test(HttpServletRequest request, HttpServletResponse response) {
        List<ZJD> ZJDS =  zjdService.findAll();
        Photo photo =  ZJDS.get(0).getPhotos().get(0);
        photo.setPath("d:/99988877.jpg");
        //photoService.deletePhoto(photo);
        zjdService.delete(ZJDS);
    }
}
