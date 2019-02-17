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
    List<TaskInfo> findAllByPfName (String pfName);
    List<TaskInfo> findAllByUserNameAndDelFlag(String username,int delFlag);
    @Override
    List<TaskInfo> findAll();
    TaskInfo findOneByTaskId(String taskId);
    List<TaskInfo> findAllByTaskStatusAndDelFlagAndUserName(int taskStatus,int delFlag,String userName);
    TaskInfo findOneByTaskName (String taskName);
}
