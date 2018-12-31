package com.example.srarsystem.repository;

import com.example.srarsystem.entity.ProjectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Chen
 * @createTime 20181028 11:30
 * @description the repository of project
 */
public interface ProjectRepository extends JpaRepository<ProjectInfo, String> {
    ProjectInfo findOneByPjId(String pjId);

    Page<ProjectInfo> findAll(Specification<ProjectInfo> specification, Pageable pageable);
}
