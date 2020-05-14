package com.xupu.zjd.service;


import com.alibaba.fastjson.JSONObject;
import com.xupu.common.YBException.ZJDException;
import com.xupu.common.po.ResultData;
import com.xupu.common.service.ResultDataService;
import com.xupu.common.tools.JSONTool;
import com.xupu.common.tools.ReflectTool;
import com.xupu.common.tools.RepositoryTool;
import com.xupu.common.tools.Tool;
import com.xupu.project.po.Project;
import com.xupu.project.po.Task;
import com.xupu.project.service.IProjectService;
import com.xupu.project.service.ITaskService;
import com.xupu.usermanager.po.User;
import com.xupu.usermanager.service.IUserService;
import com.xupu.xzqy.po.XZDM;
import com.xupu.xzqy.service.IXZDMService;
import com.xupu.zjd.dao.ZJDRepository;
import com.xupu.zjd.po.ZJD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ZJDService implements IZJDService {
    @Autowired
    private ZJDRepository zjdRepository;
    @Autowired
    private IXZDMService xzdmService;
    @Autowired
    private IProjectService projectService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ITaskService taskService;

    private ResultDataService resultDataService = ResultDataService.getResultDataService();

    @Override
    public List<ZJD> findAll() {
        return zjdRepository.findAll();
    }


    @Transactional
    @Override
    public void saveOrUpdateAll(List<ZJD> zjds) {
        if (!Tool.isEmpty(zjds)) {
            List<ZJD> oldZJDs = findAll();
            Map<String, ZJD> oldZJDMap = null;
            try {
                oldZJDMap = ReflectTool.getIDMap("getZDNUM", oldZJDs);
            } catch (ZJDException e) {
                e.printStackTrace();
            }

            Map<String, XZDM> xzdmMap = xzdmService.getDJZQDMMap();
            for (ZJD zjd : zjds) {
                String zdnum = zjd.getZDNUM();

                if (zdnum != null && zdnum.length() == 19) {
                    ZJD oldZJD = oldZJDMap.get(zdnum);
                    if (oldZJD != null) {
                        //替换原来的宅基地
                        oldZJD.setQUANLI(zjd.getQUANLI());
                        int index = zjds.indexOf(zjd);
                        zjds.set(index, oldZJD);
                        oldZJD.setBZ(zjd.getBZ());
                    }
                    String djzqdm = zdnum.replace("JA", "").replace("JB", "").replace("JC", "").substring(0, 14);
                    XZDM xzdm = xzdmMap.get(djzqdm);
                    zjd.setXzdm(xzdm);
                    zjd.setUpload(true);
                    zjd.setXzdmid(xzdm.getId());
                }
            }
            zjdRepository.saveAll(zjds);
        }
    }

    @Transactional
    @Override
    public ResultData saveOrUpdateAll2(Long projectId, List<ZJD> zjds) {


        if (!Tool.isEmpty(zjds)) {
            Project project = projectService.findById(projectId);
            if (project == null) {
                return resultDataService.getErrorResultData("数据库库中没有找到这个项目");
            }

            List<ZJD> oldZJDs = new ArrayList<>();

            for (XZDM xzdm : project.getXzdms()) {
                oldZJDs.addAll(xzdm.getZjds());
            }


            Map<String, ZJD> oldZJDMap = null;
            try {
                oldZJDMap = ReflectTool.getIDMap("getZDNUM", oldZJDs);
            } catch (ZJDException e) {
                return resultDataService.getErrorResultData(e.getMessage());
            }
            ReflectTool.MethodCustom methodCustom = ReflectTool.MethodCustom.getInstance(ZJD.class);

            Map<String, XZDM> xzdmMap = xzdmService.getDJZQDMMap(project.getXzdms());
            for (int i = 0; i < zjds.size(); i++) {

                ZJD zjd = zjds.get(i);
                zjd.setXzdmid(zjd.getXzdm().getId());
                //zjd.getXzdm().setProject(xzdmMap.get(zjd.getXzdm().getDJZQDM()).getProject());
                zjd.setXzdmid(zjd.getXzdm().getId());
                String zdnum = zjd.getZDNUM();
                ZJD oldZJD = oldZJDMap.get(zdnum);

                if (oldZJD != null) {
                    ReflectTool.replaceFiled(oldZJD, zjd, methodCustom);
                    zjds.set(i, oldZJD);
                }
            }
        }
        zjdRepository.saveAll(zjds);
        return resultDataService.getSuccessResultData(zjds);
    }


    @Override
    public void save(ZJD ZJD) {
        if (ZJD != null) {
            ZJD.setUpload(true);
            zjdRepository.save(ZJD);
        }
    }

    @Override
    public void delete(List<ZJD> ZJDS) {
        if (!Tool.isEmpty(ZJDS)) {
            zjdRepository.deleteAll(ZJDS);
        }

    }

    public ZJD findById(Long id) {
        if (id == null) {
            return null;
        }
        ZJD zjd = zjdRepository.findById(id).get();
        return zjd;
    }

    @Override
    public ZJD findByZDNUM(String zdnum) {
        List<ZJD> zjds = zjdRepository.findByZDNUM(zdnum);
        if (!Tool.isEmpty(zjds)) {
            return zjds.get(0);
        }
        return null;
    }

    @Override
    public List<ZJD> findByXZDMIds(List<Long> xzdmids) {
        if (xzdmids == null) {
            return findAll();
        }

        Specification sp = RepositoryTool.getSpecificationEqu("xzdmid", xzdmids);
        List<ZJD> list = zjdRepository.findAll(sp);
        return list;
    }

    @Transient
    @Override
    public List<ZJD> findByXZDMId(Long xzdmid) {
        return zjdRepository.findByXzdmid(xzdmid);
    }

    @Override
    public void update(ZJD zjdPo) {

    }

    @Override
    public List<ZJD> findByXZDMIds(Long id, List<Long> xzdmids) {
        List<ZJD> zjds = findByXZDMIds(xzdmids);
        if (id == null) {
            return zjds;
        }
        long idValue = id.longValue();
        List<ZJD> results = new ArrayList<>();
        if (zjds != null) {
            for (ZJD zjd : zjds
            ) {
                User user = zjd.getUsercreate();
                if (user == null || idValue == user.getId().longValue()) {
                    results.add(zjd);
                }
            }
        }
        return results;
    }

    /**
     * // 删除 宅基地
     *
     * @param zjdPo
     */
    @Override
    public void deleteZJD(ZJD zjdPo) {
        zjdRepository.delete(zjdPo);
    }

    @Override
    public List<ZJD> jsonToZJD(List<String> featureJsons) {
        List<ZJD> list = new ArrayList<>();
        for (String jsonStr : featureJsons) {
            JSONObject json = JSONObject.parseObject(jsonStr);

            String geometry = json.getString("geometry");
            //JSONObject jsonObject = JSONObject.parseObject(geometry);

            Map<String, Object> properties = json.getJSONObject("properties");
            ZJD zjd = JSONTool.toObject(properties, ZJD.class);
            zjd.setGeometry(geometry);

            list.add(zjd);
        }
        return list;
    }

    @Override
    public List<String> checkZJDs(List<ZJD> zjds) {
        List<String> list = new ArrayList<>();
        String err = "";
        for (ZJD zjd : zjds) {
            String zdnum = zjd.getZDNUM();
            String quanli = zjd.getQUANLI();
            if (zdnum == null) {
                err = "有地块没有编码";
                if (!list.contains(err)) {
                    list.add(err);
                }
            } else if (zdnum.length() != 19) {
                err = "地块编码没有达到19位：" + zdnum;
                if (!list.contains(err)) {
                    list.add(err);
                }
            }
            if (Tool.isEmpty(quanli)) {
                err = "有地块没有权利人名称";
                if (!list.contains(err)) {
                    list.add(err);
                }
            }
        }
        return list;
    }

    @Override
    public void deleteZJDByID(Long id) {

        zjdRepository.deleteById(id);
    }

    @Transactional
    @Override
    public ResultData SetUser(Long userid, List<ZJD> zjds) {
        if (Tool.isEmpty(zjds)) {
            return resultDataService.getErrorResultData("没有选择任何的宅基地");
        }
        User user = userService.findById(userid);
        for (ZJD zjd : zjds) {
            Task task = zjd.getTask();
            if (task == null) {
                task = new Task();
                user.getTasks().add(task);
                task.setUser(user);
                zjd.setTask(task);
            }else{
                task.setUser(user);
                taskService.save(task);
            }
        }
        zjdRepository.saveAll(zjds);

        return resultDataService.getSuccessResultData("");
    }
}
