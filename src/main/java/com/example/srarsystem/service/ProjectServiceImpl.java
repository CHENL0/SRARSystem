package com.example.srarsystem.service;

import com.example.srarsystem.commons.DateUtils;
import com.example.srarsystem.commons.PageToolUtils;
import com.example.srarsystem.commons.UUIDUtils;
import com.example.srarsystem.entity.ProjectInfo;
import com.example.srarsystem.repository.ProfessorRepository;
import com.example.srarsystem.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author Chen
 * @createTime 20181028 12:37
 * @description the impl of project
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProfessorRepository professorRepository;
    private final ProjectRepository projectRepository;
    private final PageToolUtils pageToolUtils;

    @Autowired
    public ProjectServiceImpl(ProfessorRepository professorRepository, ProjectRepository projectRepository, PageToolUtils pageToolUtils) {
        this.professorRepository = professorRepository;
        this.projectRepository = projectRepository;
        this.pageToolUtils = pageToolUtils;
    }

    @Override
    public void uploadProjectFile(String filePath, String fileName, String pjUser,
                                  String pjType, String pjDescription) {
        ProjectInfo projectInfo = new ProjectInfo(UUIDUtils.getUUID(), fileName,
                filePath, pjUser, pjType, pjDescription, DateUtils.getTimestamp(), 1);
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
        Specification<ProjectInfo> specification = pageToolUtils.specifucation(productType);
        Pageable pageable = PageRequest.of(page, count, sort);
        return projectRepository.findAll(specification, pageable);
    }

}
