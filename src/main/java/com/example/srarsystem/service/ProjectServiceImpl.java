package com.example.srarsystem.service;

import com.example.srarsystem.commons.DateUtils;
import com.example.srarsystem.commons.PageToolUtils;
import com.example.srarsystem.commons.UUIDUtils;
import com.example.srarsystem.entity.ProjectInfo;
import com.example.srarsystem.entity.ProjectTypeInfo;
import com.example.srarsystem.repository.ProfessorRepository;
import com.example.srarsystem.repository.ProjectRepository;
import com.example.srarsystem.repository.ProjectTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Chen
 * @createTime 20181028 12:37
 * @description the impl of project
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProfessorRepository professorRepository;
    private final ProjectRepository projectRepository;
    private final ProjectTypeRepository projectTypeRepository;
    private final PageToolUtils pageToolUtils;

    @Autowired
    public ProjectServiceImpl(ProfessorRepository professorRepository, ProjectRepository projectRepository, ProjectTypeRepository projectTypeRepository, PageToolUtils pageToolUtils) {
        this.professorRepository = professorRepository;
        this.projectRepository = projectRepository;
        this.projectTypeRepository = projectTypeRepository;
        this.pageToolUtils = pageToolUtils;
    }

    @Override
    public void uploadProjectFile(String filePath, String fileName, String pjUser,
                                  String pjType, String pjDescription) {
        ProjectInfo projectInfo = new ProjectInfo(UUIDUtils.getUUID(), fileName,
                filePath, pjUser, pjType, pjDescription, DateUtils.getTimestamp(), 1);
        projectRepository.save(projectInfo);
    }

    @Override
    public ProjectInfo findOneByPjId(String pjId) {
        return projectRepository.findOneByPjId(pjId);
    }

    @Override
    public ProjectInfo getProjectInfoByPjId(String pjId) {
        return projectRepository.findOneByPjId(pjId);
    }

    @Override
    public void saveProject(ProjectInfo projectInfo) {
        projectRepository.save(projectInfo);
    }

    @Override
    public Page<ProjectInfo> getProjectListByPage(int page, String productType, int count, Sort sort) {
        Specification<ProjectInfo> specification = pageToolUtils.pjSpecification(productType);
        Pageable pageable = PageRequest.of(page, count, sort);
        return projectRepository.findAll(specification, pageable);
    }

    @Override
    public List<ProjectTypeInfo> getAllPjInfo() {
        return projectTypeRepository.findAll();
    }

    @Override
    public List<ProjectInfo> getPjInfoListByUsername(String username) {
        return projectRepository.findProjectInfosByPjUser(username);
    }

    @Override
    public List<ProjectInfo> getPjInfoListByPfname(String pfname) {
        return projectRepository.findProjectInfosByPjReviewer(pfname);
    }

    @Override
    public void commitPjInfoData(ProjectInfo projectInfo) {
        projectRepository.save(projectInfo);
    }

    @Override
    public ProjectInfo setPjInfoData(ProjectInfo projectInfo, String file, String localPath) {
        String pjCommitDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        projectInfo.setPjId(UUIDUtils.getUUID());
        projectInfo.setPjStatus(1);
        projectInfo.setPjCommitDate(pjCommitDate);
        projectInfo.setPjName(file);
        projectInfo.setPjPath(localPath);
        return projectInfo;
    }



}
