package com.example.srarsystem.repository;

import com.example.srarsystem.entity.ProjectInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Chen
 * @createTime 20181028 11:30
 * @description the repository of project
 */
public interface ProjectRepository extends JpaRepository<ProjectInfo,String> {
}
