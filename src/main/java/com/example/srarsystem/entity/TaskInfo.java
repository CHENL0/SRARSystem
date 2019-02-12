package com.example.srarsystem.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Chen
 * @createTime 20181104 12:46
 * @description the info of task
 */
@Data
@Entity
public class TaskInfo{
    @Id
    private String taskId;
    private String userName;
    private String pfName;
    private String pjId;
    private String taskDate;
    private String deadline;
    private String countTime;
    private String taskName;
    private String taskDescription;
    private String taskFileName;
    private String taskPath;
    private String taskMessage;
    private int delFlag;
    private int delFlagAudit;
    /**
     * the status of task what 1 is haven't finished and 2 is have finished
     * and 3 is default and 4 is termination and 5 is commit
     */
    private int taskStatus;
}
