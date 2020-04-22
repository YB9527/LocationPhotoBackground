package com.xupu.usermanager.dao;

import com.xupu.usermanager.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByAccount(String account);
    Optional<User> findById(Long id);
}
