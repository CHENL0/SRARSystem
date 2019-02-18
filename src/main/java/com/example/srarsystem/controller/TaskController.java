package com.example.srarsystem.controller;

import com.example.srarsystem.commons.AccessUtils;
import com.example.srarsystem.entity.ProjectInfo;
import com.example.srarsystem.entity.TaskInfo;
import com.example.srarsystem.service.ProjectService;
import com.example.srarsystem.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
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
    private final ProjectService projectService;

    public TaskController(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
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

    @RequestMapping(value = "/getTaskInfoListData")
    public @ResponseBody
    Object getTaskInfoListData (String pfName,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        List<TaskInfo> taskInfos = taskService.getTaskInfosByPfName(pfName);
        Map<String,List<TaskInfo>> taskInfosMap = new HashMap<>();
        taskInfosMap.put("taskInfos",taskInfos);
        return taskInfosMap;
    }

    /**
     * @Description //TODO
     * @Author Chen
     * @DateTime 2018/11/3
     * @Param
     * @Return
     */
    @RequestMapping("/downloadTask")
    public @ResponseBody
    String downloadFile(HttpServletResponse response, String taskId) {
        AccessUtils.getAccessAllow(response);
        // 文件名
        TaskInfo taskInfo = taskService.getTaskInfo(taskId);
        String fileName = taskInfo.getTaskFileName();
        String realPath = taskInfo.getTaskPath();
        if (fileName != null) {
            //设置文件路径
            File file = new File(realPath , fileName);
            if (file.exists()) {
                // 设置强制下载不打开
                response.setContentType("application/force-download");
                // 设置文件名
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "下载成功";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "下载失败";
    }

    @RequestMapping("/submitTaskInfoData")
    public @ResponseBody
    Object submitTaskInfoData (HttpServletResponse response,String taskInfo) throws IOException {
        AccessUtils.getAccessAllow(response);
        ObjectMapper objectMapper = new ObjectMapper();
        TaskInfo taskInfoMapper = objectMapper.readValue(taskInfo,TaskInfo.class);
        taskService.createTaskInfo(taskInfoMapper);
        Map<String,String> responseMap = new HashMap<>();
        responseMap.put("responseType","SUCCESS");
        return responseMap;
    }

    @RequestMapping("/getUserNameList")
    public @ResponseBody
    Object getUserNameList (String pfName){
        List<ProjectInfo> projectInfos = projectService.getDistinctPjUsersBypfReviewer(pfName);
        Map<String,List<ProjectInfo>> projectInfosMap = new HashMap<>();
        projectInfosMap.put("projectInfos",projectInfos);
        return projectInfosMap;

    }
}
