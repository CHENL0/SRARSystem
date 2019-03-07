package com.example.srarsystem.controller;

/**
 * @author Chen
 * @createTime 20181017 21:51
 * @description the controller of user
 */

import com.example.srarsystem.commons.AccessUtils;
import com.example.srarsystem.commons.FileUtils;
import com.example.srarsystem.entity.ProfessorInfo;
import com.example.srarsystem.entity.TaskInfo;
import com.example.srarsystem.entity.UserInfo;
import com.example.srarsystem.service.ProfessorService;
import com.example.srarsystem.service.TaskService;
import com.example.srarsystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "/user")
//@PreAuthorize("hasRole('USER')")
public class UserController {

    private final TaskService taskService;
    private final UserService userService;
    private final ProfessorService professorService;

    public UserController(TaskService taskService, UserService userService, ProfessorService professorService) {
        this.taskService = taskService;
        this.userService = userService;
        this.professorService = professorService;
    }

    @RequestMapping(value = "/getTasks")
    @PreAuthorize("hasRole('ROLE_USER')")
    public @ResponseBody
    Object getAllTasks(String username, HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        List<TaskInfo> taskInfoList = taskService.findAllByUserNameAndDelFlag(username);
        Map<String,List<?>> tasksListMap = new HashMap<>();
        tasksListMap.put("taskInfoList", taskInfoList);
        return tasksListMap;
    }

    @RequestMapping(value = "/getUserInfo")
    @Secured({"ADMIN","USER","PROFESSOR"})
    public @ResponseBody
    Object getUserInfo (String username, HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        UserInfo userInfo = userService.findOneByUserName(username);
        Map<String,UserInfo> userInfoMap = new HashMap<>();
        userInfoMap.put("userInfo", userInfo);
        return userInfoMap;
    }

    @RequestMapping(value = "/getTaskInfo")
//    @PreAuthorize("hasRole('USER')")
    @Secured({"USER","PROFESSOR"})
    public  @ResponseBody
    Object getTaskInfo(String taskId, HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        TaskInfo taskInfo = taskService.getTaskInfo(taskId);
        Map<String, TaskInfo> taskInfoMap = new HashMap<>();
        taskInfoMap.put("taskInfo", taskInfo);
        return taskInfoMap;
    }

    @RequestMapping(value = "/getOneStatusTaskList")
    @PreAuthorize("hasRole('ROLE_USER')")
    public  @ResponseBody
    Object getOneStatusTaskList(int taskStatus,String username, HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        List<TaskInfo> oneStatusTaskList = taskService.getAllTaskInfoByTaskStatusAndDelFlag(taskStatus,username);
        Map<String, List<TaskInfo>> taskInfoMap = new HashMap<>();
        taskInfoMap.put("oneStatusTaskList", oneStatusTaskList);
        return taskInfoMap;
    }

    @RequestMapping(value = "/getPFInfoListPage")
//    @PreAuthorize("hasRole('USER')")
    @Secured({"ADMIN","USER","PROFESSOR"})
    public @ResponseBody
    Object getPFInfoListPage (@RequestParam(name = "pfType", defaultValue = "基础研究")String pfType,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        Sort sort =Sort.by("pfId");
        Page<ProfessorInfo> pfInfoListPage = professorService.getPfInfoListByPage(page,pfType,6,sort);
        Map<String , Page<ProfessorInfo>> pfInfoListPageMap = new HashMap<>();
        pfInfoListPageMap.put("pfInfoListPage",pfInfoListPage);
        return pfInfoListPageMap;
    }

    @RequestMapping(value = "/getOneTypePFInfoList")
    @Secured({"ADMIN","USER","PROFESSOR"})
    public  @ResponseBody
    Object getOneTypePFInfoList(@RequestParam(name = "pfType", defaultValue = "基础研究")String pfType,
                                HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        List<ProfessorInfo> OneTypePfInfoList = professorService.getPfInfoListByType(pfType);
        Map<String, List<ProfessorInfo>> OneTypePfInfoListMap = new HashMap<>();
        OneTypePfInfoListMap.put("OneTypePfInfoList",OneTypePfInfoList);
        return OneTypePfInfoListMap;
    }

    @RequestMapping(value = "/userProfileSubmit")
    @PreAuthorize("hasRole('USER')")
    public  @ResponseBody
    Object userProfileSubmit(MultipartFile file,String userProfileData,HttpServletResponse response) throws IOException {
        AccessUtils.getAccessAllow(response);
        Map<Object,Object> finishDataRequestMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        UserInfo userInfoMapper = objectMapper.readValue(userProfileData,UserInfo.class);
        String userName = userInfoMapper.getUserName();
        UserInfo userInfo = userService.findOneByUserName(userName);
        if(file == null){
            userService.finishUserInfoData(userInfo, userInfoMapper, null);
            finishDataRequestMap.put("responseType","SUCCESS");
        }else {
            userService.finishUserInfoData(userInfo, userInfoMapper, file.getOriginalFilename());
            String localPath = "G:/idea/MyGitPros/SRARSystem/src/main/resources/static/assets/icon";
            if(file.getOriginalFilename() != userInfo.getUserIconName()){
                if (FileUtils.upload(file, localPath, file.getOriginalFilename())){
                    //success
                    finishDataRequestMap.put("responseType","SUCCESS");
                }else {
                    //error
                    finishDataRequestMap.put("responseType","ERROR");
                }
            }else {
                finishDataRequestMap.put("responseType","SUCCESS");
            }
        }

        return finishDataRequestMap;
    }


    @RequestMapping(value = "/getAllPFInfoList")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody
    Object getAllPFInfoList (){
        List<ProfessorInfo> professorInfoList = professorService.getAllPfInfoList();
        Map<String,List<ProfessorInfo>> pfInfoListMap = new HashMap<>();
        pfInfoListMap.put("professorInfoList",professorInfoList);
        return pfInfoListMap;
    }

    @RequestMapping(value = "/commitTaskInfoData")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody
    Object commitTaskInfoData(MultipartFile file,String taskInfoData
            ,HttpServletResponse response) throws IOException {
        AccessUtils.getAccessAllow(response);
        Map<Object,Object> finishDataRequestMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        TaskInfo taskInfoMapper = objectMapper.readValue(taskInfoData,TaskInfo.class);
        String userName = taskInfoMapper.getUserName();
        String localPath = "G:/idea/MyGitPros/SRARSystem/src/main/resources/static/assets/taskFile/"+userName;
        if (FileUtils.upload(file, localPath, file.getOriginalFilename())){
            //success
            String taskName = taskInfoMapper.getTaskName();
            TaskInfo taskInfoByTaskName= taskService.getTaskInfoByTaskName(taskName);
            taskInfoByTaskName.setTaskMessage(taskInfoMapper.getTaskMessage());
            TaskInfo taskInfo =taskService.setTaskInfoData(taskInfoByTaskName,file.getOriginalFilename(),localPath);
            taskService.updateTaskInfoData(taskInfo);
            finishDataRequestMap.put("responseType","SUCCESS");
        }else {
            //error
            finishDataRequestMap.put("responseType","ERROR");
        }
        return finishDataRequestMap;
    }

    @RequestMapping(value = "/getAllUserInfoForAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    Object getAllUserInfoForAdmin(HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        List<UserInfo> userInfoList = userService.getAllUserInfo();
        Map<String,List<UserInfo>> userInfoListMap = new HashMap<>();
        userInfoListMap.put("userInfoList",userInfoList);
        return userInfoListMap;
    }

    @RequestMapping(value = "/updateDelFlagForAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    Object updateDelFlagForAdmin(HttpServletResponse response,String userId,int delFlag){
        AccessUtils.getAccessAllow(response);
        userService.updateDelFlagByUserId(userId,delFlag);
        Map<String,String> responseMap = new HashMap<>();
        responseMap.put("responseType","SUCCESS");
        return responseMap;
    }
}
