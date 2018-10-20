package com.example.srarsystem.service;

import org.springframework.stereotype.Service;

/**
 * @author Chen
 * @createTime 20181020 11:17
 * @description the interface of professorService
 */
@Service
public interface ProfessorService  {
    boolean pfLogin(String pfName,String pfPassword);
    String getPfIdByPfName(String pfName);
}
