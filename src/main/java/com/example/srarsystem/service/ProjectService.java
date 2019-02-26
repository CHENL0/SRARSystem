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

    List<ProjectTypeInfo> getAllPjTypeInfo();

    List<ProjectInfo> getPjInfoListByUsername(String username);

    List<ProjectInfo> getPjInfoListByPfname(String pfname);

    void commitPjInfoData (ProjectInfo projectInfo);

    ProjectInfo setPjInfoData (ProjectInfo projectInfo, String file, String localPath);

    List<ProjectInfo> getAllPjInfos(String pjType);

    List<ProjectInfo> getAllPjInfosByQuery(String pjTitle,String pjType);

    List<ProjectInfo> getDistinctPjUsersBypfReviewer (String pfReviewer);

    List<ProjectInfo> getAllByPjReviewerAndPjUser(String pjReviewer,String pjUser);

    List<ProjectInfo> getAllByPjTitle (String pjTitle);

    void saveProjectTypeInfo (ProjectTypeInfo projectTypeInfo);

    void updateProjectTypeInfo (ProjectTypeInfo projectTypeInfo);

    ProjectTypeInfo getOnePjTypeByPjTypeId (String pjTypeId);

    void deletePjTypeInfoByPjTypeId (String pjTypeId);



}
