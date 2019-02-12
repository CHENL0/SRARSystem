package com.example.srarsystem.controller;

import com.example.srarsystem.commons.AccessUtils;
import com.example.srarsystem.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chen
 * @createTime 02 13:29
 * @description
 */
@Controller
@RequestMapping(value = "/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = "/deleteTaskInfoDataForUser")
    public @ResponseBody
    Object deleteTaskInfoDataForUser(String taskId,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        taskService.deleteTaskInfoDataByTaskIdForUser(taskId);
        Map<String,String> responseMap = new HashMap<>();
        responseMap.put("responseType","SUCCESS");
        return responseMap;

    }

    @RequestMapping(value = "/deleteTaskInfoDataForAudit")
    public @ResponseBody
    Object deleteTaskInfoDataForAudit(String taskId,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        taskService.deleteTaskInfoDataByTaskIdForAudit(taskId);
        Map<String,String> responseMap = new HashMap<>();
        responseMap.put("responseType","SUCCESS");
        return responseMap;

    }
}
