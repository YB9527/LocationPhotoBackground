package com.xupu.usermanager.service;

import com.alibaba.fastjson.JSONObject;
import com.xupu.common.po.Redis;
import com.xupu.common.po.ResultData;
import com.xupu.common.po.Status;
import com.xupu.common.service.IRedisService;
import com.xupu.common.service.ResultDataService;
import com.xupu.common.tools.DateTool;
import com.xupu.common.tools.Tool;
import com.xupu.usermanager.dao.UserRepository;
import com.xupu.usermanager.po.Level;
import com.xupu.usermanager.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataUnit;
import org.thymeleaf.util.DateUtils;

import javax.xml.crypto.Data;
import java.util.*;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    private static ResultDataService resultDataService = ResultDataService.getResultDataService();
    @Autowired
    private IRedisService redisService;
    //redis 中 后台密码的  mark
    public static String levelMark = "level";
    private static List<JSONObject> levels;

    /**
     * 启动时 创建 级别
     */
    public UserService() {
        levels = new ArrayList<>();
        // String str = Level.manager.toString();
        levels.add(JSONObject.parseObject(Level.manager.toString()));
        levels.add(JSONObject.parseObject(Level.employee.toString()));
        levels.add(JSONObject.parseObject(Level.government.toString()));
        levels.add(JSONObject.parseObject(Level.peasant.toString()));
    }

    @Override
    public User login(User user) {
        User repositoryUser = userRepository.findByAccount(user.getAccount());
        if (repositoryUser == null) {
            return null;
        }
        if (repositoryUser.getPassword().equals(user.getPassword())) {
            return repositoryUser;
        }
        return null;
    }

    /**
     * @param user
     * @return 如果没有问题返回null
     */
    public String checkUser(User user) {
        if (Tool.isEmpty(user.getNickName())) {
            return ("昵称为空");

        }
        if (Tool.isEmpty(user.getAccount())) {

            return ("账号为空");
        }
        if (Tool.isEmpty(user.getPassword())) {
            return ("密码为空");
        }
        if (Tool.isEmpty(user.getEmail())) {
            return ("邮箱为空");
        }
        return null;
    }

    public ResultData registUser(User user) {
        String tip = checkUser(user);
        if (!Tool.isEmpty(tip)) {
            return resultDataService.getErrorResultData(tip);
        }

        User databaseUser = userRepository.findByAccount(user.getAccount());
        if (databaseUser != null) {
            if (databaseUser.getAccount().equals(user.getAccount())
                    && databaseUser.getPassword().equals(user.getPassword())
                    && user.getNickName().equals(databaseUser.getNickName())) {
                return resultDataService.getErrorResultData(user.getNickName() + ",你好，账号等待管理员同意中，请稍等！！！");
            } else {
                return resultDataService.getErrorResultData("账号已经被注册");
            }

        } else {
            user.setRegistDate(DateTool.dataFormat(new Date()));
            userRepository.save(user);
            return resultDataService.getSuccessResultData(user);
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public ResultData editUser(User user) {
        String tip = checkUser(user);
        if (!Tool.isEmpty(tip)) {
            return resultDataService.getErrorResultData(tip);
        }
        User databaseUser = findById(user.getId());
        if (databaseUser == null) {
            return resultDataService.getErrorResultData("用户已经不存在了，无法编辑");
        } else {
            userRepository.saveAndFlush(user);
            return resultDataService.getSuccessResultData(user);
        }
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<JSONObject> findLevels() {
        return levels;
    }
}
