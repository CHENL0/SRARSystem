package com.example.srarsystem.repository;

import com.example.srarsystem.entity.TaskInfo;
import javafx.concurrent.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Chen
 * @createTime 20181004 16:16
 * @description the repository of task
 */
public interface TaskRepository extends JpaRepository<TaskInfo, String> {

    List<TaskInfo> findAllByUserName(String username);
    @Override
    List<TaskInfo> findAll();
    TaskInfo findOneByTaskId(String taskId);
    List<TaskInfo> findAllByTaskStatus(int taskStatus);
}
