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

    List<TaskInfo> getAllTaskInfo();
}
