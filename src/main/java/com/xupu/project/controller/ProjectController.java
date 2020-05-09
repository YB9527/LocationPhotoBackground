package com.xupu.project.controller;

import com.google.gson.Gson;
import com.xupu.common.YBException.ZJDException;
import com.xupu.common.po.ResultData;
import com.xupu.common.service.ResultDataService;
import com.xupu.common.tools.Tool;
import com.xupu.project.po.Project;
import com.xupu.project.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
        if(projectPo  == null){
            return  resultDataService.getErrorResultData("没有可增加的项目");
        }
      return  projectService.addProject(projectPo);

    }
    @GetMapping(value = "findall")
    public ResultData findAll() {

        return  resultDataService.getSuccessResultData(projectService.findAll());

    }
    @GetMapping(value = "deletebyid")
    public ResultData deleteById(String id) {
        try {
            Long idL = Tool.parseLong(id);
            return  projectService.deleteById(idL);
        } catch (ZJDException e) {
          return   resultDataService.getErrorResultData(e.getMessage());
        }
    }

    /**
     * 修改项目人员
     * @param project
     * @return
     */
    @PostMapping(value = "/updateprojectuser")
    @ResponseBody
    public ResultData updateProjectUser(String project) {

        Project projectPo = new Gson().fromJson(project, Project.class);
        if(projectPo  == null){
            return  resultDataService.getErrorResultData("没有收到项目对象");
        }
        return  projectService.updateProjectUser(projectPo);
    }
}
