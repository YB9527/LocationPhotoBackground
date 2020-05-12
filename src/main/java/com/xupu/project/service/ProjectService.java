package com.xupu.project.service;

import com.xupu.common.YBException.ZJDException;
import com.xupu.common.po.ResultData;
import com.xupu.common.po.Status;
import com.xupu.common.service.ResultDataService;
import com.xupu.common.tools.ReflectTool;
import com.xupu.common.tools.Tool;
import com.xupu.project.dao.ProjectRepository;
import com.xupu.project.po.Project;
import com.xupu.usermanager.po.User;
import com.xupu.usermanager.service.UserService;
import com.xupu.xzqy.po.XZDM;
import com.xupu.xzqy.service.XZDMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService implements IProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private XZDMService xzdmService;

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
        List<Project> projects = projectRepository.findAll();
        return projects;
    }

    @Override
    public ResultData deleteById(Long id) {
        projectRepository.deleteById(id);
        return resultDataService.getSuccessMessageResultData("删除成功");
    }

    @Transactional
    @Override
    public ResultData updateProjectUser(Project project) {
        Project oldProject = projectRepository.findById(project.getId()).get();
        List<User> addUsers = new ArrayList<>();
        try {
            //查询哪些是新增的人员
            Map<Long, User> oldUserMap = ReflectTool.getIDMap("getId", oldProject.getUsers());
            for (User newUser : project.getUsers()) {
                //查新增的
                User oldUser = oldUserMap.get(newUser.getId());
                if (oldUser == null) {
                    addUsers.add(newUser);
                    newUser.getProjects().add(oldProject);
                }
            }
            //查删除的人员 ，检查该人员是否有 涉及的新增区域
            Map<Long, User> newUserMap = ReflectTool.getIDMap("getId", project.getUsers());
            for (int i = 0; i < oldProject.getUsers().size(); i++) {
                User oldUser = oldProject.getUsers().get(i);
                User newUser = newUserMap.get(oldUser.getId());
                //查删除的
                if (newUser == null) {
                    //被删除的user
                    oldProject.getUsers().remove(oldUser);
                    i--;
                }
            }
            oldProject.getUsers().addAll(addUsers);
            projectRepository.save(oldProject);
           /* Project oldProject2 = projectRepository.findById(project.getId()).get();
            oldProject2 = projectRepository.findById(project.getId()).get();
            userService.saveUsers(addUsers);*/
        } catch (ZJDException e) {
            return resultDataService.getErrorResultData(e.getMessage());
        }
        return resultDataService.getSuccessResultData(oldProject);
    }

    @Override
    public ResultData importXZDMs(Long id, List<XZDM> newxzdms) {
        Project project = projectRepository.findById(id).get();
        List<XZDM> oldxzdms = project.getXzdms();
        for (XZDM newxzdm : newxzdms) {
            newxzdm.setProject(project);
            newxzdm.setProjectId(id);
        }
        return xzdmService.saveOrUpdate(oldxzdms, newxzdms);

    }



    @Override
    public ResultData findByIdToResultData(Long idL) {

        Project project = projectRepository.findById(idL).get();
        return resultDataService.getSuccessResultData(project);
    }

    @Override
    public Project findById(Long idL) {

        Project project = projectRepository.findById(idL).get();
        return project;
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
        } else {
            if (Tool.isEmpty(project.getDjzqdm())) {
                return resultDataService.getErrorResultData("项目没有行政区域代码");
            }
            try {
                Long dm = Tool.parseLong(project.getDjzqdm());
            } catch (ZJDException e) {
                return resultDataService.getErrorResultData("行政区域只能时数字");
            }
        }
        return resultDataService.getSuccessResultData("");
    }
}
