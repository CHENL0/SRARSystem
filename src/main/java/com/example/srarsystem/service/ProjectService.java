package com.example.srarsystem.service;

import com.example.srarsystem.entity.ProjectInfo;
import com.example.srarsystem.entity.ProjectTypeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Chen
 * @createTime 20181028 12:35
 * @description the interface of  project serveice
 */
public interface ProjectService {
    /**
     * @Description //TODO  upload
     * @Author Chen
     * @DateTime 2018/11/17
     * @Param
     * @Return
     */
    void uploadProjectFile(String FilePath, String FileName, String pjUser, String pjType, String pjDescription);

    /**
     * @Description //TODO  find projectinfo by pjId
     * @Author Chen
     * @DateTime 2018/11/17
     * @Param
     * @Return
     */
    ProjectInfo findOneByPjId(String pjId);

    /**
     * @Description //TODO  get projectInfo by PjId
     * @Author Chen
     * @DateTime 2018/11/17
     * @Param
     * @Return
     */
    ProjectInfo getProjectInfoByPjId(String pjId);

    /**
     * @Description //TODO  get project list by page
     * @Author Chen
     * @DateTime 2018/11/17
     * @Param
     * @Return
     */
    void saveProject(ProjectInfo projectInfo);

    /**
     * @Description //TODO  get project list by page
     * @Author Chen
     * @DateTime 2018/11/17
     * @Param
     * @Return
     */
    Page<ProjectInfo> getProjectListByPage(int page, String projectType, int count, Sort sort);

    List<ProjectTypeInfo> getAllPjInfo();

    List<ProjectInfo> getPjInfoListByUsername(String username);
}
