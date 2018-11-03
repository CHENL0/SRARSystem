package com.example.srarsystem.service;

import com.example.srarsystem.commons.DateUtils;
import com.example.srarsystem.commons.UUIDUtils;
import com.example.srarsystem.entity.ProjectInfo;
import com.example.srarsystem.repository.ProfessorRepository;
import com.example.srarsystem.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Chen
 * @createTime 20181028 12:37
 * @description the impl of project
 */
public class ProjectServiceImpl implements ProjectService{

    private final ProfessorRepository professorRepository;
    private final ProjectRepository projectRepository;
    @Autowired
    public ProjectServiceImpl(ProfessorRepository professorRepository, ProjectRepository projectRepository) {
        this.professorRepository = professorRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void uplodaProjectFile(String filePath, String fileName ,String pjUser,String pjDescription) {
        ProjectInfo projectInfo = new ProjectInfo(UUIDUtils.getUUID(),fileName,filePath,pjUser,pjDescription,DateUtils.getTimestamp(),1);
    }

    @Override
    public String getPjNameByPjId(String pjId) {
        ProjectInfo projectInfo = projectRepository.getProjectInfoByPjId(pjId);
        return projectInfo.getPjName();
    }

    @Override
    public ProjectInfo getProjectInfoByPjId(String pjId) {
        return projectRepository.getProjectInfoByPjId(pjId);
    }

    @Override
    public void saveProject(ProjectInfo projectInfo) {
        projectRepository.save(projectInfo);
    }
}
