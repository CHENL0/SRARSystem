package com.example.srarsystem.controller;

/**
 * @author Chen
 * @createTime 20181017 21:51
 * @description the controller of user
 */

import com.example.srarsystem.entity.ProfessorInfo;
import com.example.srarsystem.entity.TaskInfo;
import com.example.srarsystem.entity.UserInfo;
import com.example.srarsystem.service.ProfessorService;
import com.example.srarsystem.service.TaskService;
import com.example.srarsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final TaskService taskService;
    private final UserService userService;
    private final ProfessorService professorService;

    public UserController(TaskService taskService, UserService userService, ProfessorService professorService) {
        this.taskService = taskService;
        this.userService = userService;
        this.professorService = professorService;
    }

    public Object commitTask() {
        return null;
    }

    @RequestMapping(value = "/getTasks")
    public @ResponseBody
    Object getAllTasks(String username){
        List<TaskInfo> taskInfoList = taskService.getAllTaskInfoByUsername(username);
        Map<String,List<?>> tasksListMap = new HashMap<>();
        tasksListMap.put("taskInfoList", taskInfoList);
        return tasksListMap;
    }

    @RequestMapping(value = "/getUserInfo")
    public @ResponseBody
    Object getUserInfo (String username){
        UserInfo userInfo = userService.findOneByUserName(username);
        Map<String,UserInfo> userInfoMap = new HashMap<>();
        userInfoMap.put("userInfo", userInfo);
        return userInfoMap;
    }

    @RequestMapping(value = "/getTaskInfo")
    public  @ResponseBody
    Object getTaskInfo(String taskId){
        TaskInfo taskInfo = taskService.getTaskInfo(taskId);
        Map<String, TaskInfo> taskInfoMap = new HashMap<>();
        taskInfoMap.put("taskInfo", taskInfo);
        return taskInfoMap;
    }

    @RequestMapping(value = "/getOneStatusTaskList")
    public  @ResponseBody
    Object getOneStatusTaskList(int taskStatus){
        List<TaskInfo> oneStatusTaskList = taskService.getAllTaskInfoByTaskStatus(taskStatus);
        Map<String, List<?>> taskInfoMap = new HashMap<>();
        taskInfoMap.put("oneStatusTaskList", oneStatusTaskList);
        return taskInfoMap;
    }

    @RequestMapping(value = "/getPFInfoListPage")
    public @ResponseBody
    Object getPFInfoListPage (String pfType,int page){
        Sort sort = new Sort(Sort.Direction.DESC, "pfId");
        Page<ProfessorInfo> pfInfoListPage = professorService.getPfInfoListByPage(page,pfType,6,sort);
        Map<String , Page<ProfessorInfo>> pfInfoListPageMap = new HashMap<>();
        pfInfoListPageMap.put("pfInfoListPage",pfInfoListPage);
        return pfInfoListPageMap;
    }
}
