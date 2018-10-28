package com.example.srarsystem.service;

import com.example.srarsystem.commons.UUIDUtils;
import com.example.srarsystem.entity.ProjectInfo;
import com.example.srarsystem.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * @author Chen
 * @createTime 20181028 12:37
 * @description the impl of project
 */
public class ProjectServiceImpl implements ProjectService{

    private final ProfessorRepository professorRepository;
    @Autowired
    public ProjectServiceImpl(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Override
    public void uplodaProjectFile(String filePath, String fileName ,String pjUser,String pjDescription) {
        ProjectInfo projectInfo = new ProjectInfo(UUIDUtils.getUUID(),fileName,filePath,pjUser,pjDescription,1);
    }
}
