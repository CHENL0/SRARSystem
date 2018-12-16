package com.example.srarsystem.controller;

import com.example.srarsystem.commons.DateUtils;
import com.example.srarsystem.entity.ProjectInfo;
import com.example.srarsystem.entity.TaskInfo;
import com.example.srarsystem.service.ProjectService;
import com.example.srarsystem.service.TaskService;
import com.example.srarsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Chen
 * @createTime 20181002 22:30
 * @description the controller of professor
 */
@Controller
public class ProfessorController {

    private final ProjectService projectService;
    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public ProfessorController(ProjectService projectService, UserService userService, TaskService taskService) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
    }

    @PostMapping(value = "/getAllProject")
    public @ResponseBody
    Page<ProjectInfo> getAllProjectPage(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNum,
                                        @RequestParam(name = "dataCount", defaultValue = "3") int dataCount,
                                        @RequestParam(name = "pjType", defaultValue = "基础研究") String pjType) {
        Sort sort = Sort.by("pjType");
        Page<ProjectInfo> projectPage = projectService.getProjectListByPage(pageNum,pjType,dataCount, sort);

        return projectPage;
    }

    /**
     * @Description //TODO reject the project
     * @Author Chen
     * @DateTime 2018/11/3
     * @Param
     * @Return
     */
    @PostMapping(value = "/rejectProject")
    public Object rejectProject(String pjId) {
        ProjectInfo projectInfo = projectService.getProjectInfoByPjId(pjId);
        projectInfo.setPjStatus(3);
        projectService.saveProject(projectInfo);
        return null;
    }

    /**
     * @Description //TODO
     * @Author Chen
     * @DateTime 2018/11/3
     * @Param
     * @Return
     */
    @PostMapping(value = "/acceptProject")
    public Object acceptProject(String pjId) {
        ProjectInfo projectInfo = projectService.getProjectInfoByPjId(pjId);
        projectInfo.setPjStatus(2);
        projectService.saveProject(projectInfo);
        return null;
    }

    /**
     * @Description //TODO
     * @Author Chen
     * @DateTime 2018/11/3
     * @Param
     * @Return
     */
    @PostMapping(value = "/addTask")
    public Object addTask(TaskInfo taskInfo){
        taskService.addTask(taskInfo);
        return "success";
    }

    /**
     * @Description //TODO the scheduled what it will run to update the taskStatus every day 0 clock
     * @Author Chen
     * @DateTime 2018/11/3
     * @Param
     * @Return
     */
    @Scheduled (cron = "0 0 0 * * ?")
    public void changeTaskByTime(){
        List<TaskInfo> TaskList = taskService.getAllTaskInfo();
        Iterator iterator = TaskList.iterator();
        while(iterator.hasNext()){
            TaskInfo taskInfo= (TaskInfo) iterator.next();
            if(taskInfo.getTaskStatus() == 1){
                String deadline = taskInfo.getDeadline();
                Date getParseDate = DateUtils.parseDateTime(deadline);
                if (getParseDate.before(new Date())){
                    taskInfo.setTaskStatus(3);
                    taskService.addTask(taskInfo);
                }
            }
        }
    }

    /**
     * @Description //TODO
     * @Author Chen
     * @DateTime 2018/11/3
     * @Param
     * @Return
     */
    @PostMapping("/download")
    public @ResponseBody
    String downloadFile(HttpServletRequest request, HttpServletResponse response, String pjId) {
        // 文件名
        ProjectInfo projectInfo = projectService.findOneByPjId(pjId);
        String fileName = projectInfo.getPjName();
        String realPath = projectInfo.getPjPath();
        if (fileName != null) {
            //设置文件路径
            File file = new File("E:\\f/" + fileName);
            //File file = new File(realPath , fileName);
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
}
