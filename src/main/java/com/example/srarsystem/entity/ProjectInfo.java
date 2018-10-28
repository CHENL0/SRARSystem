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
    private int pjStatus;

//    public ProjectInfo() {
//    }
    public ProjectInfo(String pjId, String pjName, String pjPath, String pjUser, String pjDescription, int pjStatus) {
        this.pjId = pjId;
        this.pjName = pjName;
        this.pjPath = pjPath;
        this.pjUser = pjUser;
        this.pjDescription = pjDescription;
        this.pjStatus = pjStatus;
    }
}
