package com.example.srarsystem.service;

import com.example.srarsystem.entity.TaskInfo;
import com.example.srarsystem.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Chen
 * @createTime 20181004 16:28
 * @description the impl of taskService
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * @Description //TODO to add new task
     * @Author Chen
     * @DateTime
     * @Param
     * @Return
     */
    @Override
    public void addTask(TaskInfo taskInfo) {
        taskRepository.save(taskInfo);
    }

    /**
     * @Description //TODO get list of all task
     * @Author Chen
     * @DateTime
     * @Param
     * @Return
     */
    @Override
    public List<TaskInfo> findAllByUserNameAndDelFlag(String username) {
        return taskRepository.findAllByUserNameAndDelFlag(username,0);
    }

    @Override
    public List<TaskInfo> getAllTaskInfoByTaskStatusAndDelFlag(int taskStatus,String username) {
        return taskRepository.findAllByTaskStatusAndDelFlagAndUserName(taskStatus,0,username);
    }

    @Override
    public List<TaskInfo> getAllTaskInfo() {
        return taskRepository.findAll();
    }

    @Override
    public TaskInfo getTaskInfo(String taskId) {
        return taskRepository.findOneByTaskId(taskId);
    }

    @Override
    public TaskInfo setTaskInfoData(TaskInfo taskInfo, String file, String localPath) {
        taskInfo.setTaskFileName(file);
        taskInfo.setTaskPath(localPath);
        taskInfo.setTaskStatus(5);
        return taskInfo;
    }

    @Override
    public void updateTaskInfoData(TaskInfo taskInfo) {
        taskRepository.save(taskInfo);
    }

    @Override
    public TaskInfo getTaskInfoByTaskName(String taskName) {
        return taskRepository.findOneByTaskName(taskName);
    }

    @Override
    public void deleteTaskInfoDataByTaskIdForUser(String taskId) {
        TaskInfo taskInfo = taskRepository.findOneByTaskId(taskId);
        taskInfo.setDelFlag(1);
        taskRepository.save(taskInfo);
    }

    @Override
    public void deleteTaskInfoDataByTaskIdForAudit(String taskId) {
        TaskInfo taskInfo = taskRepository.findOneByTaskId(taskId);
        taskInfo.setDelFlagAudit(1);
        taskRepository.save(taskInfo);
    }

    @Override
    public List<TaskInfo> getTaskInfosByPfName(String pfName) {
        return taskRepository.findAllByPfName(pfName);

    }
}
