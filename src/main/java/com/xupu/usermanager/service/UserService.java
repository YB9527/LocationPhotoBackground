package com.xupu.usermanager.service;

import com.xupu.usermanager.dao.UserRepository;
import com.xupu.usermanager.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService  implements  IUserService{
    @Autowired
    private UserRepository userRepository;


    @Override
    public User login(User user) {
        User repositoryUser  =  userRepository.findByAccount(user.getAccount());
        if(repositoryUser == null){
            return  null;
        }
        if(repositoryUser.getPassword().equals(user.getPassword())){
            return  repositoryUser;
        }
        return null;
    }
}
