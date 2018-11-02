package com.example.srarsystem.controller;

import com.example.srarsystem.entity.ProjectInfo;
import com.example.srarsystem.entity.UserInfo;
import com.example.srarsystem.service.ProjectService;
import com.example.srarsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author Chen
 * @createTime 20181002 22:30
 * @description the controller of professor
 */
@Controller
public class ProfessorController {

    private final ProjectService projectService;
    private final UserService userService;
    @Autowired
    public ProfessorController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    public Object rejectProject(HttpServletRequest request,String pjId){
        String userId = (String) request.getSession().getAttribute("userId");
        UserInfo userInfo = userService.getUserInfoByUserId(userId);
        ProjectInfo projectInfo = projectService.getProjectInfoByPjId(pjId);
        return null;
    }

    @PostMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response,String pjId) {
        String fileName = projectService.getPjNameByPjId(pjId);// 文件名
        if (fileName != null) {
            //设置文件路径
            File file = new File("/Users/dalaoyang/Documents/"+fileName);
            //File file = new File(realPath , fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
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
