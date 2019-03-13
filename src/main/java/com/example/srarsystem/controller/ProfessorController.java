package com.example.srarsystem.controller;

import com.example.srarsystem.commons.AccessUtils;
import com.example.srarsystem.commons.DateUtils;
import com.example.srarsystem.commons.FileUtils;
import com.example.srarsystem.entity.ProfessorInfo;
import com.example.srarsystem.entity.ProjectInfo;
import com.example.srarsystem.entity.TaskInfo;
import com.example.srarsystem.service.ProfessorService;
import com.example.srarsystem.service.ProjectService;
import com.example.srarsystem.service.TaskService;
import com.example.srarsystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @author Chen
 * @createTime 20181002 22:30
 * @description the controller of professor
 */
@Controller
@RequestMapping(value = "/pf")
public class ProfessorController {

    private final ProjectService projectService;
    private final ProfessorService professorService;
    private final TaskService taskService;

    @Autowired
    public ProfessorController(ProjectService projectService, ProfessorService professorService, TaskService taskService) {
        this.projectService = projectService;
        this.professorService = professorService;
        this.taskService = taskService;
    }

    @PostMapping(value = "/getAllProject")
    public @ResponseBody
    Page<ProjectInfo> getAllProjectPage(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNum,
                                        @RequestParam(name = "dataCount", defaultValue = "3") int dataCount,
                                        @RequestParam(name = "pjType", defaultValue = "基础研究") String pjType,
                                        HttpServletResponse response) {
        AccessUtils.getAccessAllow(response);
        Sort sort = Sort.by("pjType");
        Page<ProjectInfo> projectPage = projectService.getProjectListByPage(pageNum, pjType, dataCount, sort);

        return projectPage;
    }

    /**
     * @Description //TODO change the projectStatus
     * @Author Chen
     * @DateTime 2018/11/3
     * @Param
     * @Return
     */
    @PostMapping(value = "/changeProjectStatus")
    public Object rejectProject(String pjId, HttpServletResponse response,int pjStatus) {
        AccessUtils.getAccessAllow(response);
        ProjectInfo projectInfo = projectService.getProjectInfoByPjId(pjId);
        if(pjStatus == 4){
            professorService.changePfSuccessCount(projectInfo.getPjReviewer());
        }
        projectInfo.setPjStatus(pjStatus);
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
    public Object addTask(TaskInfo taskInfo, HttpServletResponse response) {
        AccessUtils.getAccessAllow(response);
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
    @Scheduled(cron = "0 0 0 * * ?")
    public void changeTaskByTime() {
        List<TaskInfo> TaskList = taskService.getAllTaskInfo();
        Iterator iterator = TaskList.iterator();
        while (iterator.hasNext()) {
            TaskInfo taskInfo = (TaskInfo) iterator.next();
            if (taskInfo.getTaskStatus() == 1) {
                String deadline = taskInfo.getDeadline();
                Date getParseDate = DateUtils.parseDateTime(deadline);
                if (getParseDate.before(new Date())) {
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
        AccessUtils.getAccessAllow(response);
        // 文件名
        ProjectInfo projectInfo = projectService.findOneByPjId(pjId);
        String fileName = projectInfo.getPjName();
        String realPath = projectInfo.getPjPath();
        if (fileName != null) {
            //设置文件路径
//            File file = new File("E:\\f/" + fileName);
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


    @RequestMapping(value = "/getPfInfoData")
    @Secured({"ADMIN","USER","PROFESSOR"})
    public @ResponseBody
    Object getPfInfoData(String pfName) {
        ProfessorInfo professorInfo = professorService.findOneByPfName(pfName);
        Map<String, ProfessorInfo> professorInfoMap = new HashMap<>();
        professorInfoMap.put("professorInfo", professorInfo);
        return professorInfoMap;
    }

    @RequestMapping(value = "/pfProfileSubmit")
    @PreAuthorize("hasRole('PROFESSOR')")
    public  @ResponseBody
    Object pfProfileSubmit(MultipartFile file, String pfProfileData, HttpServletResponse response) throws IOException {
        AccessUtils.getAccessAllow(response);
        Map<Object,Object> finishDataRequestMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        ProfessorInfo pfInfoMapper = objectMapper.readValue(pfProfileData,ProfessorInfo.class);
        String pfName = pfInfoMapper.getPfName();
        ProfessorInfo pfInfo = professorService.findOneByPfName(pfName);
        if(file == null){
            professorService.finishProfessorInfoData(pfInfo, pfInfoMapper, null);
            finishDataRequestMap.put("responseType","SUCCESS");
        }else {

            // 项目根目录
//            String localPath = "G:/idea/MyGitPros/SRARSystem/src/main/resources/static/assets/icon";
            //本地目录
            String localPath = "E:/pjStatic/icon";
            if(pfInfo.getPfPicture() != file.getOriginalFilename()){
                if (FileUtils.upload(file, localPath, file.getOriginalFilename())){
                    professorService.finishProfessorInfoData(pfInfo, pfInfoMapper, file.getOriginalFilename());
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


    /**
     * @Description  //TODO get pj by professorName for check pj
     * @Author Chen
     * @DateTime 2019/1/19
     * @Param
     * @Return
     */
    @RequestMapping(value = "/getPjInfoListByPfName")
    @PreAuthorize("hasRole('PROFESSOR')")
    public @ResponseBody
    Object getPjInfoListByPfName (String pfName,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        List<ProjectInfo> onePfPjInfoList = projectService.getPjInfoListByPfname(pfName);
        Map<String, List<ProjectInfo>> onePfPjInfoListMap = new HashMap<>();
        onePfPjInfoListMap.put("onePfPjInfoList", onePfPjInfoList);
        return onePfPjInfoListMap;
    }

    @RequestMapping(value = "/getAllPfInfoForAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    Object getAllPfInfoForAdmin(HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        List<ProfessorInfo> pfInfoList = professorService.getAllPfInfo();
        Map<String,List<ProfessorInfo>>pfInfoListMap = new HashMap<>();
        pfInfoListMap.put("pfInfoList",pfInfoList);
        return pfInfoListMap;
    }

    @RequestMapping(value = "/updateDelFlagForAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    Object updateDelFlagForAdmin(HttpServletResponse response,String pfId,int delFlag){
        AccessUtils.getAccessAllow(response);
        professorService.updateDelFlagByPfId(pfId,delFlag);
        Map<String,String> responseMap = new HashMap<>();
        responseMap.put("responseType","SUCCESS");
        return responseMap;
    }

    @RequestMapping(value = "/validatePfInfo")
    @PreAuthorize("hasRole('ROLE_USER')")
    public @ResponseBody
    Object validatePfInfo(HttpServletResponse response,String pfName,String userName){
        AccessUtils.getAccessAllow(response);
        boolean IsStatus = professorService.validatePf(userName,pfName);
        Map<String,Boolean> responseMap = new HashMap<>();
        responseMap.put("responseType",IsStatus);
        return responseMap;
    }

    /**
     * @Description  //TODO get pj by professorName for check pj
     * @Author Chen
     * @DateTime 2019/1/19
     * @Param
     * @Return
     */
    @RequestMapping(value = "/changePfTypeByAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    Object changePfTypeByAdmin (String pfName,String pfType,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        professorService.changePfTypeByPfNameAndPfType(pfName,pfType);
        Map<String,String> responseMap = new HashMap<>();
        responseMap.put("responseType","SUCCESS");
        return responseMap;
    }
}
