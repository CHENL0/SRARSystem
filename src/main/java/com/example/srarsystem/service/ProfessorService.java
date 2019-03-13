package com.example.srarsystem.service;

import com.example.srarsystem.entity.ProfessorInfo;
import com.example.srarsystem.entity.ProjectInfo;
import com.example.srarsystem.entity.UserInfo;
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

    void finishProfessorInfoData (ProfessorInfo pfInfo,ProfessorInfo pfInfoMapper,String fileName);

    void changePfSubmitCount (String pfName);

    void changePfSuccessCount (String pfName);

    void changePfTypeByPfNameAndPfType(String pfName, String pfType);

    String createPfInfoAndSave (UserInfo userInfo,String selectedType);

    int projectTypeNumber (String pjType);

    List<ProfessorInfo> getAllPfInfo ();

    void updateDelFlagByPfId (String pfId,int delFlag);

    boolean validatePf (String pfName,String userName);
}
