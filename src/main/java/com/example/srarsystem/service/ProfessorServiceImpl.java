package com.example.srarsystem.service;

import com.example.srarsystem.entity.ProfessorInfo;
import com.example.srarsystem.entity.ProjectInfo;
import com.example.srarsystem.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Chen
 * @createTime 20181020 11:18
 * @description the impl of professorService
 */
@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;

    @Autowired
    public ProfessorServiceImpl(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Override
    public boolean pfLogin(String pfName, String pfPassword) {
        ProfessorInfo professorInfo = professorRepository.findByPfNameAndPfPassword(pfName, pfPassword);
        if (!professorInfo.equals("") && professorInfo != null) {
            return true;
        }
        return false;
    }

    @Override
    public ProfessorInfo findOneByPfName(String pfName) {
        return professorRepository.findOneByPfName(pfName);
    }
}
