package com.example.srarsystem.entity;

import com.example.srarsystem.commons.DateUtils;
import com.example.srarsystem.commons.UUIDUtils;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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
    private String pjTitle;
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

//    @OneToOne(cascade = {CascadeType.ALL,})
//    @JoinColumn(name="pjId")
//    private ProjectInfo projectInfo;

    public TaskInfo() {
        this.taskId = UUIDUtils.getUUID();
        this.taskDate = DateUtils.parseStringTime(new Date());
        this.countTime = "1";
        this.delFlag = 0;
        this.delFlagAudit = 0;
        this.taskStatus = 1;
    }
}
