package com.xupu.cbd.service;

import com.xupu.cbd.dao.UserRepository;
import com.xupu.cbd.po.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Service
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void test(){
        Date data = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(data);

        userRepository.save(new User("aa1","aa1234561","aa@126.com1","aa1",formattedDate));
        userRepository.save(new User("bb1","bb1234561","bb@126.com1","bb1",formattedDate));
        userRepository.save(new User("cc1","cc1234561","cc@126.com1","cc1",formattedDate));
        String aa = "dd";
      // Double d =  Double.parseDouble(aa);
        // Assert.assertEquals(3,userRepository.findAll().size());
        //Assert.assertEquals("bb",userRepository.findByUserNameOrEmail("bb","bb@126.com").getNickName());
        //userRepository.delete(userRepository.findByUserName("aa"));

    }
}
