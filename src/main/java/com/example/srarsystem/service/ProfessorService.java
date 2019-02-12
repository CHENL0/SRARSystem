package com.example.srarsystem.service;

import com.example.srarsystem.entity.ProfessorInfo;
import com.example.srarsystem.entity.ProjectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Chen
 * @createTime 20181020 11:17
 * @description the interface of professorService
 */
public interface ProfessorService {
    boolean pfLogin(String pfName, String pfPassword);

    ProfessorInfo findOneByPfName(String pfName);

    Page<ProfessorInfo> getPfInfoListByPage (int page, String pfType, int count, Sort sort);

    List<ProfessorInfo> getPfInfoListByType (String pfType);

    List<ProfessorInfo> getAllPfInfoList ();

    void changePfSubmitCount (String pfName);

    void changePfSuccessCount (String pfName);
}
