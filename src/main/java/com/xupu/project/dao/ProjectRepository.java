package com.xupu.project.dao;

import com.xupu.project.po.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {
       Project findProjectByName(String name);

       Optional<Project> findById(Long id);
}
