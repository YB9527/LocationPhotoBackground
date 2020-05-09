package com.xupu.usermanager.dao;

import com.xupu.usermanager.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByAccount(String account);
    Optional<User> findById(Long id);

    @Query(value = "SELECT * FROM user WHERE  `level` is not null", nativeQuery = true)
    List<User> findRegistUsers();
}
