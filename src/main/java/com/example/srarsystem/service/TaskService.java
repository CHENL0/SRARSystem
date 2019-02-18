package com.example.srarsystem.service;

import com.example.srarsystem.entity.TaskInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Chen
 * @createTime 20181104 16:18
 * @description the interface of task service
 */
public interface TaskService {
    void addTask(TaskInfo taskInfo);
    List<TaskInfo> findAllByUserNameAndDelFlag(String username);
    List<TaskInfo> getAllTaskInfoByTaskStatusAndDelFlag(int taskStatus,String username);
    List<TaskInfo> getAllTaskInfo();
    TaskInfo getTaskInfo(String taskId);
    TaskInfo setTaskInfoData (TaskInfo taskInfo, String file, String localPath);
    void createTaskInfo (TaskInfo taskInfo);
    void updateTaskInfoData (TaskInfo taskInfo);
    TaskInfo getTaskInfoByTaskName (String taskName);
    void deleteTaskInfoDataByTaskIdForUser(String taskId);
    void deleteTaskInfoDataByTaskIdForAudit(String taskId);
    List<TaskInfo> getTaskInfosByPfName(String pfName);
}
