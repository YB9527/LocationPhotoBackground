package com.xupu.project.service;

import com.xupu.common.po.ResultData;
import com.xupu.common.po.Status;
import com.xupu.common.service.ResultDataService;
import com.xupu.common.tools.Tool;
import com.xupu.project.dao.ProjectRepository;
import com.xupu.project.po.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService implements IProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    private ResultDataService resultDataService = ResultDataService.getResultDataService();

    @Override
    public ResultData addProject(Project project) {
        ResultData resultData = checkProject(project);
        if (resultData.getStatus() == Status.Error) {
            return resultData;
        }
        Project databasePrj = projectRepository.findProjectByName(project.getName());

        //检查项目名字是否重复

        if (databasePrj != null) {
            //如果有id ，如果id不相等，一样报错
            if (project.getId() == null || project.getId().longValue() != databasePrj.getId().longValue()) {
                return resultDataService.getErrorResultData("项目名称重复");
            }
        }

        project = projectRepository.save(project);
        return resultDataService.getSuccessResultData(project);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public ResultData deleteById(Long id) {
        projectRepository.deleteById(id);
        return resultDataService.getSuccessMessageResultData("删除成功");
    }


    /**
     * 检查 项目
     *
     * @param project
     * @return
     */
    private ResultData checkProject(Project project) {
        if (Tool.isEmpty(project.getName())) {
            return resultDataService.getErrorResultData("项目没有名字");
        } else if (Tool.isEmpty(project.getProjectType())) {
            return resultDataService.getErrorResultData("项目没有类型");
        }
        return resultDataService.getSuccessResultData("");
    }
}
