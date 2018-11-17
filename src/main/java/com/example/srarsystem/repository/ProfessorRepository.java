package com.example.srarsystem.repository;

import com.example.srarsystem.entity.ProfessorInfo;
import com.example.srarsystem.entity.ProjectInfo;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Chen
 * @createTime 20181020 11:13
 * @description the repository of professor
 */
public interface ProfessorRepository extends JpaRepository<ProfessorInfo,String> {
    ProfessorInfo findByPfNameAndPfPassword(String pfName ,String pfPassword);
    ProfessorInfo findOneByPfName(String PfName);
}
