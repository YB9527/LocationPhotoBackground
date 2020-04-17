package com.xupu.usermanager.dao;

import com.xupu.usermanager.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByAccount(String account);
}
