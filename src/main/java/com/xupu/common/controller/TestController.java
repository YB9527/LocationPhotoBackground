package com.xupu.common.controller;

import com.xupu.zjd.dao.ZJDRepository;
import com.xupu.zjd.po.ZJD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    @Autowired
    private ZJDRepository zjdRepository;
    @GetMapping(value = "shape")
    public String shape(){
        List<ZJD> zjds = zjdRepository.findAll();
        return "Hello Springboot";
    }
}
