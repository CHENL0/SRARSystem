package com.example.srarsystem.repository;

import com.example.srarsystem.entity.ProjectTypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Chen
 * @createTime 2019107 23:21
 * @description the repository of project
 */
public interface ProjectTypeRepository extends JpaRepository<ProjectTypeInfo,String> {
//    List<ProjectTypeInfo> getAll();
    ProjectTypeInfo findOneByPjTypeId (String pjTypeId);
    List<ProjectTypeInfo> findAllByDelFlag(int delFlag);
}
