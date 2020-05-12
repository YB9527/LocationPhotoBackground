package com.xupu.project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xupu.common.YBException.ZJDException;
import com.xupu.common.po.ResultData;
import com.xupu.common.service.ResultDataService;
import com.xupu.common.tools.Tool;
import com.xupu.project.po.Project;
import com.xupu.project.service.IProjectService;
import com.xupu.xzqy.po.XZDM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/project")
public class ProjectController {
    @Autowired
    private IProjectService projectService;
    private ResultDataService resultDataService = ResultDataService.getResultDataService();

    @PostMapping(value = "/addproject")
    @ResponseBody
    public ResultData addproject(String project) {

        Project projectPo = Tool.getGson().fromJson(project, Project.class);
        if (projectPo == null) {
            return resultDataService.getErrorResultData("没有可增加的项目");
        }
        return projectService.addProject(projectPo);

    }


    @GetMapping(value = "findall")
    public ResultData findAll() {
        return resultDataService.getSuccessResultData(projectService.findAll());
    }

    @GetMapping(value = "deletebyid")
    public ResultData deleteById(String id) {
        try {
            Long idL = Tool.parseLong(id);
            return projectService.deleteById(idL);
        } catch (ZJDException e) {
            return resultDataService.getErrorResultData(e.getMessage());
        }
    }

    @GetMapping(value = "findbyid")
    public ResultData findbyid(String projectid) {
        try {
            Long idL = Tool.parseLong(projectid);
            return projectService.findByIdToResultData(idL);
        } catch (ZJDException e) {
            return resultDataService.getErrorResultData(e.getMessage());
        }
    }

    /**
     * 修改项目人员
     *
     * @param project
     * @return
     */
    @PostMapping(value = "/updateprojectuser")
    @ResponseBody
    public ResultData updateProjectUser(String project) {

        Project projectPo = new Gson().fromJson(project, Project.class);
        if (projectPo == null) {
            return resultDataService.getErrorResultData("没有收到项目对象");
        }
        return projectService.updateProjectUser(projectPo);
    }

    /**
     * 导入行政代码表
     *
     * @return
     */
    @PostMapping(value = "/importxzdms")
    @ResponseBody
    public ResultData importxzdms(String projectid, String xzdms) {
        List<XZDM> xzdmList = Tool.getGson().fromJson(xzdms, new TypeToken<List<XZDM>>() {
        }.getType());
        if (Tool.isEmpty(xzdmList)) {
            return resultDataService.getErrorResultData("没有传任何数据过来");
        }
        try {
            Long id = Tool.parseLong(projectid);
            return projectService.importXZDMs(id, xzdmList);
        } catch (ZJDException e) {
            return resultDataService.getErrorResultData(e.getMessage());
        }
        //return resultDataService.getSuccessResultData("");
    }



}
