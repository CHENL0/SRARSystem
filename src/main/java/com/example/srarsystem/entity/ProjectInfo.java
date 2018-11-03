package com.example.srarsystem.entity;

import lombok.Data;

/**
 * @author Chen
 * @createTime 20181028 10:54
 * @description 28
 */
@Data
public class ProjectInfo {
    private String pjId;
    private String pjName;
    private String pjPath;
    private String pjUser;
    private String pjReviewer;
    private String pjDescription;
    private String pjCommitDate;
    /**
     *  status have 1,2 ,3   1 is accept,2 is pass,3 is reject
     */
    private int pjStatus;

//    public ProjectInfo() {
//    }
    public ProjectInfo(String pjId, String pjName, String pjPath, String pjUser, String pjDescription,String pjCommitDate, int pjStatus) {
        this.pjId = pjId;
        this.pjName = pjName;
        this.pjPath = pjPath;
        this.pjUser = pjUser;
        this.pjDescription = pjDescription;
        this.pjStatus = pjStatus;
        this.pjCommitDate = pjCommitDate;
    }
}
