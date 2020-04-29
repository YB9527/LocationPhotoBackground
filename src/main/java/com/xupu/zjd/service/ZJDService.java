package com.xupu.zjd.service;


import com.alibaba.fastjson.JSONObject;
import com.xupu.common.YBException.ZJDException;
import com.xupu.common.tools.JSONTool;
import com.xupu.common.tools.ReflectTool;
import com.xupu.common.tools.RepositoryTool;
import com.xupu.common.tools.Tool;
import com.xupu.usermanager.po.User;
import com.xupu.xzqy.po.XZDM;
import com.xupu.xzqy.service.IXZDMService;
import com.xupu.zjd.dao.ZJDRepository;
import com.xupu.zjd.po.ZJD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ZJDService implements IZJDService {
    @Autowired
    private ZJDRepository zjdRepository;
    @Autowired
    private IXZDMService xzdmService;


    @Override
    public List<ZJD> findAll() {
        return zjdRepository.findAll();
    }

    @Transactional
    @Override
    public void saveOrUpdateAll(List<ZJD> zjds) {
        if (!Tool.isEmpty(zjds)) {
            List<ZJD> oldZJDs = findAll();
            Map<String,ZJD> oldZJDMap = null;
            try {
                oldZJDMap = ReflectTool.getIDMap("getZDNUM",oldZJDs);
            } catch (ZJDException e) {
                e.printStackTrace();
            }

            Map<String, XZDM> xzdmMap = xzdmService.getDJZQDMMap();
            for (ZJD zjd : zjds) {
                String zdnum = zjd.getZDNUM();

                if (zdnum != null && zdnum.length() == 19) {
                    ZJD oldZJD = oldZJDMap.get(zdnum);
                    if(oldZJD != null){
                        //替换原来的宅基地
                        oldZJD.setQUANLI(zjd.getQUANLI());
                        int index = zjds.indexOf(zjd);
                        zjds.set(index,oldZJD);
                        oldZJD.setBZ(zjd.getBZ());
                    }
                    String djzqdm = zdnum.replace("JA", "").replace("JB", "").replace("JC","").substring(0, 14);
                    XZDM xzdm = xzdmMap.get(djzqdm);
                    zjd.setXzdm(xzdm);
                    zjd.setUpload(true);
                    zjd.setDJZQDM(djzqdm);
                }
            }
            zjdRepository.saveAll(zjds);
        }
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
    public List<ZJD> findByDJZQDM(List<String> djzqdms) {
        if (djzqdms == null) {
            return findAll();
        }

        Specification sp = RepositoryTool.getSpecification("DJZQDM", djzqdms);
        List<ZJD> list = zjdRepository.findAll(sp);
        return list;
    }

    @Override
    public void update(ZJD zjdPo) {

    }

    @Override
    public List<ZJD> findByDJZQDM(Long id, List<String> djzqdms) {
        List<ZJD> zjds = findByDJZQDM(djzqdms);
        if (id == null) {
            return zjds;
        }
        long idValue = id.longValue();
        List<ZJD> results = new ArrayList<>();
        if (zjds != null) {
            for (ZJD zjd : zjds
            ) {
                User user = zjd.getUser();
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
    public void deleteZJDByID(Long id ) {

        zjdRepository.deleteById(id);
    }
}
