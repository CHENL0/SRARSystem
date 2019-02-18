package com.example.srarsystem.repository;

import com.example.srarsystem.entity.ProjectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Chen
 * @createTime 20181028 11:30
 * @description the repository of project
 */
public interface ProjectRepository extends JpaRepository<ProjectInfo, String> {
    ProjectInfo findOneByPjId(String pjId);

    Page<ProjectInfo> findAll(Specification<ProjectInfo> specification, Pageable pageable);

    List<ProjectInfo> findProjectInfosByPjUser(String pjUser);

    List<ProjectInfo> findProjectInfosByPjReviewer(String  pjReviewer);


    @Query("SELECT p FROM ProjectInfo p where p.pjType=?1 and p.pjStatus = '2' ")
    List<ProjectInfo> findAllByPjTypeAndPjStatus (String pjType);

    List<ProjectInfo> findAllByPjStatusAndPjTitleContainingAndPjType(int pjStatus,String pjTitle,String pjType);

    @Query("SELECT distinct p.pjUser FROM ProjectInfo p where p.pjReviewer=?1")
    List<ProjectInfo> findAllByPjReviewer(String pjReviewer);
}
