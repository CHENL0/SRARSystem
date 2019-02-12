package com.example.srarsystem.service;

import com.example.srarsystem.commons.PageToolUtils;
import com.example.srarsystem.entity.ProfessorInfo;
import com.example.srarsystem.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Chen
 * @createTime 20181020 11:18
 * @description the impl of professorService
 */
@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;
    private final PageToolUtils pageToolUtils;

    @Autowired
    public ProfessorServiceImpl(ProfessorRepository professorRepository, PageToolUtils pageToolUtils) {
        this.professorRepository = professorRepository;
        this.pageToolUtils = pageToolUtils;
    }

    @Override
    public boolean pfLogin(String pfName, String pfPassword) {
        ProfessorInfo professorInfo = professorRepository.findByPfNameAndPfPassword(pfName, pfPassword);
        if (professorInfo != null) {
            return true;
        }
        return false;
    }

    @Override
    public ProfessorInfo findOneByPfName(String pfName) {
        return professorRepository.findOneByPfName(pfName);
    }

    @Override
    public Page<ProfessorInfo> getPfInfoListByPage(int page, String pfType, int count, Sort sort) {
        Specification<ProfessorInfo> specification = pageToolUtils.pfSpecification(pfType);
        Pageable pageable = PageRequest.of(page, count, sort);
        return professorRepository.findAll(specification, pageable);
    }

    @Override
    public List<ProfessorInfo> getPfInfoListByType(String pfType) {
        List<ProfessorInfo> OneTypePfInfoList = professorRepository.findAllByPfType(pfType);
        return OneTypePfInfoList;
    }

    @Override
    public List<ProfessorInfo> getAllPfInfoList() {
        return professorRepository.findAll();
    }

    @Override
    public void changePfSubmitCount(String pfName) {
        ProfessorInfo professorInfo = findOneByPfName(pfName);
        professorInfo.setPfSubmitCount(professorInfo.getPfSubmitCount()+1);
        professorRepository.save(professorInfo);
    }

    @Override
    public void changePfSuccessCount(String pfName) {
        ProfessorInfo professorInfo = findOneByPfName(pfName);
        professorInfo.setPfSuccessCount(professorInfo.getPfSuccessCount()+1);
        professorRepository.save(professorInfo);
    }
}
