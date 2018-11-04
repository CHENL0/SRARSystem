package com.example.srarsystem.entity;

import lombok.Data;

/**
 * @author Chen
 * @createTime 20181104 12:46
 * @description the info of task
 */
@Data
public class TaskInfo {
    private String taskId;
    private String userName;
    private String pfName;
    private String taskDate;
    private String deadline;
    private String countTime;
    /**
     *  the status of task what 1 is haven't finished and 2 is have finished and 3 is default and 4 is termination
     */
    private String taskStatus;
}
