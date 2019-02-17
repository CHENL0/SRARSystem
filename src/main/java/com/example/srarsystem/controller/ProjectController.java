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
    Object getAllPjTypeInfos(HttpServletResponse response) {
        AccessUtils.getAccessAllow(response);
        List<ProjectTypeInfo> projectInfoList = projectService.getAllPjTypeInfo();
        Map<String, List<?>> pjInfoListMap = new HashMap<>();
        pjInfoListMap.put("pjInfoList", projectInfoList);
        return pjInfoListMap;
    }

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
