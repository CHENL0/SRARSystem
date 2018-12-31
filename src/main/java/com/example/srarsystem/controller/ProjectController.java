package com.example.srarsystem.controller;

import com.example.srarsystem.entity.UserInfo;
import com.example.srarsystem.service.ProjectService;
import com.example.srarsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author Chen
 * @createTime 20181028 11:07
 * @description the controller of project
 */
@Slf4j
@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam MultipartFile file, String pjType,
                         @RequestParam("pjDescription") String pjDescription, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        UserInfo userInfo = userService.getUserInfoByUserId(userId);
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        String fileName = file.getOriginalFilename();
        String filePath = "E:\\f/";
//        String filePath = "D:" + File.separator + "apache-tomcat-8.5.15"+ File.separator + "files" ;
//        String realPath = File.separator + "home" + File.separator + "tomcat" + File.separator + "apache-tomcat-9.0.1" + File.separator + "files"
        File cuFilePath = new File(filePath);
        if (!cuFilePath.isDirectory()) {
            cuFilePath.mkdir();
        }
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            projectService.uploadProjectFile(filePath, fileName, userInfo.getUserName(), pjType, pjDescription);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传成功";


    }

}
