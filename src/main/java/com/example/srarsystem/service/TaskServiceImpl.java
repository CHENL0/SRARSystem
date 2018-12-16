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
    public List<TaskInfo> getAllTaskInfo() {
        return taskRepository.findAll();
    }
}
