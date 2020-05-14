package com.xupu.common.service;

import com.alibaba.fastjson.JSONObject;
import com.xupu.common.po.Media;
import com.xupu.common.po.ResultData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMediaService {
    /**
     * 获取所有的 多媒体类型
     * @return
     */
    List<JSONObject> findMediaTypes();

    /**
     * 获取当前项目的 多媒体 数据
     * @param projectid
     * @return
     */
    List<Media> findMedias(Long projectid);

    ResultData addMedia(Long projectid, Media media);

    void deleteById(Long mediaid);

    /**
     * 修改meida
     * @param media
     * @return
     */
    ResultData upDateMedia(Media media);

    Media findById(Long id);
}
