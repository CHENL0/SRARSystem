package com.example.srarsystem.repository;

import com.example.srarsystem.entity.TaskInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Chen
 * @createTime 20181004 16:16
 * @description the repository of task
 */
public interface TaskRepository extends JpaRepository<TaskInfo,String> {
}
