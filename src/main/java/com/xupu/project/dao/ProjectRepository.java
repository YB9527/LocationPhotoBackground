package com.xupu.project.dao;

import com.xupu.project.po.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
       Project findProjectByName(String name);
}
