package com.example.srarsystem.service;

import com.example.srarsystem.commons.DateUtils;
import com.example.srarsystem.commons.PageToolUtils;
import com.example.srarsystem.commons.UUIDUtils;
import com.example.srarsystem.entity.ProfessorInfo;
import com.example.srarsystem.entity.ProjectInfo;
import com.example.srarsystem.entity.UserInfo;
import com.example.srarsystem.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        ProfessorInfo professorInfo = professorRepository.findOneByPfName(pfName);
        if (encoder.matches(pfPassword,professorInfo.getPfPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public ProfessorInfo findOneByPfName(String pfName) {
        return professorRepository.findOneByPfName(pfName);
    }

    @Override
    public void finishProfessorInfoData(ProfessorInfo pfInfo, ProfessorInfo pfInfoMapper, String fileName) {
        pfInfo.setPfNickname(pfInfoMapper.getPfNickname());
        pfInfo.setPfAddress(pfInfoMapper.getPfAddress());
        pfInfo.setPfIntroduce(pfInfoMapper.getPfIntroduce());
        pfInfo.setPfGender(pfInfoMapper.getPfGender());
        if(fileName != null){
            pfInfo.setPfPicture(fileName);
        }
        professorRepository.save(pfInfo);
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

    @Override
    public String createPfInfoAndSave(UserInfo userInfo,String selectedType) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        ProfessorInfo professorInfo = new ProfessorInfo();
        String timestamp = DateUtils.getTimestamp();
        String pfName = "PROFESSOR_" + timestamp;
        professorInfo.setPfId(UUIDUtils.getUUID());
        professorInfo.setPfName(pfName);
        professorInfo.setPfAddress(userInfo.getUserAddress());
        professorInfo.setPfGender(userInfo.getUserGender());
        professorInfo.setPfPassword(encoder.encode(userInfo.getUserPassword()));
        professorInfo.setPfPhone(userInfo.getUserPhone());
        professorInfo.setPfSecurityQuestion(userInfo.getUrSecurityQuestion());
        professorInfo.setPfSecurityAnswer(userInfo.getUrSecurityAnswer());
        professorInfo.setPfType(selectedType);
        professorRepository.save(professorInfo);
        return pfName;
    }

    @Override
    public int projectTypeNumber(String pjType) {
        List<ProfessorInfo> professorInfos = professorRepository.findAllByPfType(pjType);
        return professorInfos.size();
    }

    @Override
    public List<ProfessorInfo> getAllPfInfo() {
        return professorRepository.findAll();
    }

    @Override
    public void updateDelFlagByPfId(String pfId, int delFlag) {
        ProfessorInfo professorInfo = professorRepository.findOneByPfId(pfId);
        professorInfo.setDelFlag(delFlag);
        professorRepository.save(professorInfo);
    }

    @Override
    public boolean validatePf(String userName,String pfName) {
        ProfessorInfo professorInfo =professorRepository.findOneByPfNameAndUserName(pfName,userName);
        if(professorInfo ==null || professorInfo.equals("")){
            return true;
        }
        return false;
    }
}
