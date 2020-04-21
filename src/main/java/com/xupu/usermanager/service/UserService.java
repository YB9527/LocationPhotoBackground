package com.xupu.usermanager.service;

import com.xupu.common.po.ResultData;
import com.xupu.common.po.Status;
import com.xupu.common.service.ResultDataService;
import com.xupu.common.tools.DateTool;
import com.xupu.common.tools.Tool;
import com.xupu.usermanager.dao.UserRepository;
import com.xupu.usermanager.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataUnit;
import org.thymeleaf.util.DateUtils;

import javax.xml.crypto.Data;
import java.util.Date;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    private static ResultDataService resultDataService = ResultDataService.getResultDataService();

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

    public ResultData registUser(User user) {

        if (Tool.isEmpty(user.getNickName())) {
            return resultDataService.getErrorResultData("昵称为空");

        }
        if (Tool.isEmpty(user.getAccount())) {

            return resultDataService.getErrorResultData("账号为空");
        }
        if (Tool.isEmpty(user.getPassword())) {
            return resultDataService.getErrorResultData("密码为空");
        }
        if (Tool.isEmpty(user.getEmail())) {
            return resultDataService.getErrorResultData("邮箱为空");
        }
        User databaseUser = userRepository.findByAccount(user.getAccount());
        if (databaseUser != null) {
            if (databaseUser.getAccount().equals(user.getAccount())
                    && databaseUser.getPassword().equals(user.getPassword())
                    && user.getNickName().equals(databaseUser.getNickName()) ) {
                return resultDataService.getErrorResultData(user.getNickName() + ",你好，账号等待管理员同意中，请稍等！！！");
            } else {
                return resultDataService.getErrorResultData("账号已经被注册");
            }

        } else {
            user.setRegistDate(new Date());
            userRepository.save(user);
            return resultDataService.getSuccessResultData(user);
        }
    }

}
