package com.xupu.project.service;

import com.xupu.common.po.ResultData;
import com.xupu.project.po.Project;

import java.util.List;

public interface IProjectService {
    /**
     * 添加项目
     * @param project
     * @return  json 中是 保存到数据库中的地块
     */
    ResultData addProject(Project project);

    /**
     * 查询所有的项目
     * @return
     */
    List<Project> findAll();

    /**
     * 通过id删除项目
     * @param id
     * @return
     */
    ResultData deleteById(Long id);

    ResultData updateProjectUser(Project projectPo);
}
