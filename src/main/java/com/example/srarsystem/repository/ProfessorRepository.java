package com.example.srarsystem.repository;

import com.example.srarsystem.entity.ProfessorInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Chen
 * @createTime 20181020 11:13
 * @description the repository of professor
 */
public interface ProfessorRepository extends JpaRepository<ProfessorInfo,String> {
    ProfessorInfo findByPfNameAndPfPassword(String pfName ,String pfPassword);
    String getPfIdByPfName(String PfName);
}
