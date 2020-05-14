package com.xupu.project.service;

import com.alibaba.fastjson.JSONObject;
import com.xupu.common.service.ResultDataService;
import com.xupu.project.dao.MediaRepository;
import com.xupu.project.po.Media;
import com.xupu.common.po.ResultData;
import com.xupu.common.tools.ReflectTool;
import com.xupu.project.po.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MediaService implements IMediaService {

    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private IProjectService projectService;
    private ResultDataService resultDataService = ResultDataService.getResultDataService();
    private static List<JSONObject> mediaTypes;

    public MediaService() {
        if (mediaTypes == null) {
            mediaTypes = new ArrayList<>();
            JSONObject json = new JSONObject();
            json.put("mediaType",0);
            json.put("message","照片");
            mediaTypes.add(json);
            json = new JSONObject();
            json.put("mediaType",1);
            json.put("message","视频");
            mediaTypes.add(json);
            json = new JSONObject();
            json.put("mediaType",2);
            json.put("message","语音");
            mediaTypes.add(json);
        }
    }

    @Override
    public List<JSONObject> findMediaTypes() {
        return mediaTypes;
    }

    @Override
    public List<Media> findMedias(Long projectid) {
        Project project = projectService.findById(projectid);
        return project.getMedias();
    }

    @Override
    public ResultData addMedia(Long projectid, Media media) {
        Project project = projectService.findById(projectid);

        if(!project.getMedias().contains(media)){
            project.getMedias().add(media);
            //projectService.addMedia(project);
            media.setProject(project);
            mediaRepository.save(media);
        }else{
            return resultDataService.getErrorResultData("多媒体重复不能添加");

        }
        return resultDataService.getSuccessResultData(media);
    }

    @Override
    public void  deleteById(Long mediaid) {
        mediaRepository.deleteById(mediaid);


    }

    @Override
    public ResultData upDateMedia(Media media) {
        Media oldMedia = findById(media.getId());
        ReflectTool.replaceFiled(oldMedia,media, ReflectTool.MethodCustom.getInstance(Media.class));
        mediaRepository.saveAndFlush(oldMedia);
        return resultDataService.getSuccessResultData(media);
    }

    @Override
    public Media findById(Long id) {
        return mediaRepository.findById(id).get();
    }

}
