package com.example.srarsystem.repository;

import com.example.srarsystem.entity.ProfessorInfo;
import com.example.srarsystem.entity.ProjectTypeInfo;
import com.example.srarsystem.service.ProfessorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Chen
 * @createTime 20181020 11:13
 * @description the repository of professor
 */
public interface ProfessorRepository extends JpaRepository<ProfessorInfo, String> {
    ProfessorInfo findByPfNameAndPfPassword(String pfName, String pfPassword);
    ProfessorInfo findOneByPfName(String PfName);
    ProfessorInfo findOneByPfId(String pfId);
    Page<ProfessorInfo> findAll (Specification<ProfessorInfo> specification, Pageable pageable);
    List<ProfessorInfo> findAllByPfType(String pfType);
    @Override
    List<ProfessorInfo> findAll();
    ProfessorInfo findOneByPfNameAndUserName(String pfName,String userName);

}
