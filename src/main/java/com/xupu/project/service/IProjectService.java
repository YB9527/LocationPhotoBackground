package com.xupu.project.service;

import com.xupu.common.po.ResultData;
import com.xupu.project.po.Project;
import com.xupu.xzqy.po.XZDM;

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

    /**
     * 修改项目的 user
     * @param projectPo
     * @return
     */
    ResultData updateProjectUser(Project projectPo);

    /**
     * 导入项目行政代码
     * @param id
     * @param newxzdms 新添加的行政代码
     * @return
     */
    ResultData importXZDMs(Long id, List<XZDM> newxzdms);

   ;

    ResultData findByIdToResultData(Long idL);

    Project findById(Long idL);


    void addMedia(Project project);
}
