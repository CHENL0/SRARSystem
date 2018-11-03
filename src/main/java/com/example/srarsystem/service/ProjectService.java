package com.example.srarsystem.service;

import com.example.srarsystem.entity.ProjectInfo;
import org.springframework.stereotype.Service;

/**
 * @author Chen
 * @createTime 20181028 12:35
 * @description the interface of  project serveice
 */
@Service
public interface ProjectService {
    void  uplodaProjectFile(String FilePath,String FileName,String pjUser,String pjDescription);
    String getPjNameByPjId(String pjId);
    ProjectInfo getProjectInfoByPjId(String pjId);
    void saveProject(ProjectInfo projectInfo);
}
