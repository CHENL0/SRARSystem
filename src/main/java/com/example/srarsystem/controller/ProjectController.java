package com.example.srarsystem.controller;

import com.example.srarsystem.commons.AccessUtils;
import com.example.srarsystem.commons.FileUtils;
import com.example.srarsystem.entity.ProjectInfo;
import com.example.srarsystem.entity.ProjectTypeInfo;
import com.example.srarsystem.service.ProfessorService;
import com.example.srarsystem.service.ProjectService;
import com.example.srarsystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chen
 * @createTime 20181028 11:07
 * @description the controller of project
 */
@Slf4j
@Controller
@RequestMapping(value = "/pj")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final ProfessorService professorService;

    @Autowired
    public ProjectController(ProjectService projectService, UserService userService, ProfessorService professorService) {
        this.projectService = projectService;
        this.userService = userService;
        this.professorService = professorService;
    }

    @GetMapping("/getPjInfoList")
    public @ResponseBody
    Object getAllPjInfos(HttpServletResponse response) {
        AccessUtils.getAccessAllow(response);
        List<ProjectTypeInfo> projectInfoList = projectService.getAllPjInfo();
        Map<String, List<?>> pjInfoListMap = new HashMap<>();
        pjInfoListMap.put("pjInfoList", projectInfoList);
        return pjInfoListMap;
    }

//    @PostMapping("/upload")
//    public @ResponseBody
//    String upload(@RequestParam MultipartFile file, String pjType,
//                  @RequestParam("pjDescription") String pjDescription,
//                  HttpServletRequest request,HttpServletResponse response) {
//        AccessUtils.getAccessAllow(response);
//        String userId = (String) request.getSession().getAttribute("userId");
//        UserInfo userInfo = userService.getUserInfoByUserId(userId);
//        if (file.isEmpty()) {
//            return "上传失败，请选择文件";
//        }
//        String fileName = file.getOriginalFilename();
//        String filePath = "E:/f/";
////        String filePath = "D:" + File.separator + "apache-tomcat-8.5.15"+ File.separator + "files" ;
////        String realPath = File.separator + "home" + File.separator + "tomcat" + File.separator + "apache-tomcat-9.0.1" + File.separator + "files"
//        File cuFilePath = new File(filePath);
//        if (!cuFilePath.isDirectory()) {
//            cuFilePath.mkdir();
//        }
//        File dest = new File(filePath + fileName);
//        try {
//            file.transferTo(dest);
//            projectService.uploadProjectFile(filePath, fileName, userInfo.getUserName(), pjType, pjDescription);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "上传成功";
//    }

    /**
     * @Description  //TODO userProfiles show oneself pjInfos
     * @Author Chen
     * @DateTime 2019/1/15
     * @Param
     * @Return
     */
    @PostMapping(value = "/getPjInfoListByUsername")
    public @ResponseBody
    Object getPjInfoListByUser(String username,HttpServletResponse response) {
        AccessUtils.getAccessAllow(response);
        List<ProjectInfo> oneUserPjInfoList = projectService.getPjInfoListByUsername(username);
        Map<String, List<ProjectInfo>> oneUserPjInfoListMap = new HashMap<>();
        oneUserPjInfoListMap.put("oneUserPjInfoList", oneUserPjInfoList);
        return oneUserPjInfoListMap;
    }

    @RequestMapping(value = "/commitPjInfoData")
    public @ResponseBody
    Object commitPjInfoData(MultipartFile file,String projectInfoData
            ,HttpServletResponse response) throws IOException {
        AccessUtils.getAccessAllow(response);
        Map<Object,Object> finishDataRequestMap = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        ProjectInfo projectInfoMapper = objectMapper.readValue(projectInfoData,ProjectInfo.class);
        String userName = projectInfoMapper.getPjUser();
        String localPath = "G:/idea/MyGitPros/SRARSystem/src/main/resources/static/assets/file/"+userName;
        if (FileUtils.upload(file, localPath, file.getOriginalFilename())){
            //success
            ProjectInfo projectInfo =projectService.setPjInfoData(projectInfoMapper,file.getOriginalFilename(),localPath);
            projectService.saveProject(projectInfo);
            professorService.changePfSubmitCount(projectInfo.getPjReviewer());
            finishDataRequestMap.put("responseType","SUCCESS");
        }else {
            //error
            finishDataRequestMap.put("responseType","ERROR");
        }
        return finishDataRequestMap;
    }

    @RequestMapping(value = "/getOnePjInfoData")
    public @ResponseBody
    Object getOnePjInfoData(String pjId,HttpServletResponse response) throws IOException {
        AccessUtils.getAccessAllow(response);
        ProjectInfo projectInfo = projectService.findOneByPjId(pjId);
        Map<String, ProjectInfo> projectInfoMap = new HashMap<>();
        projectInfoMap.put("projectInfo", projectInfo);
        return projectInfoMap;
    }
}
