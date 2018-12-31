package com.example.srarsystem.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Chen
 * @createTime 20181028 10:54
 * @description 28
 */
@Data
@Entity
public class ProjectInfo {
    @Id
    private String pjId;
    private String pjName;
    private String pjPath;
    private String pjUser;
    private String pjType;
    private String pjReviewer;
    private String pjDescription;
    private String pjCommitDate;
    /**
     * status have 1,2 ,3   1 is accept,2 is pass,3 is reject
     */
    private int pjStatus;

    //    public ProjectInfo() {
//    }
    public ProjectInfo(String pjId, String pjName, String pjPath, String pjUser, String pjType,
                       String pjDescription, String pjCommitDate, int pjStatus) {
        this.pjId = pjId;
        this.pjName = pjName;
        this.pjPath = pjPath;
        this.pjUser = pjUser;
        this.pjType = pjType;
        this.pjDescription = pjDescription;
        this.pjStatus = pjStatus;
        this.pjCommitDate = pjCommitDate;
    }
}
