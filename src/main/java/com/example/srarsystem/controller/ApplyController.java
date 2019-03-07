package com.example.srarsystem.controller;

import com.example.srarsystem.commons.AccessUtils;
import com.example.srarsystem.commons.FileUtils;
import com.example.srarsystem.entity.ApplyInfo;
import com.example.srarsystem.entity.ProfessorInfo;
import com.example.srarsystem.entity.UserInfo;
import com.example.srarsystem.service.ApplyService;
import com.example.srarsystem.service.ProfessorService;
import com.example.srarsystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chen
 * @createTime 12 17:17
 * @description
 */
@Controller
@RequestMapping(value = "/apply")
public class ApplyController {
    @Autowired
    private ApplyService applyService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProfessorService professorService;

    @RequestMapping(value = "/submitApplyInfoData")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody
    Object submitApplyInfoData(MultipartFile file, String applyInfoData
            ,HttpServletResponse response) throws IOException {
        AccessUtils.getAccessAllow(response);
        Map<Object,Object> finishDataRequestMap = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        ApplyInfo applyInfoMapper = objectMapper.readValue(applyInfoData,ApplyInfo.class);
        String userName = applyInfoMapper.getApplyUser();
        String localPath = "G:/idea/MyGitPros/SRARSystem/src/main/resources/static/assets/applyFile/"+userName;
        if (FileUtils.upload(file, localPath, file.getOriginalFilename())){
            //success
            ApplyInfo applyInfo =applyService.setApplyInfoData(applyInfoMapper,file.getOriginalFilename(),localPath);
            applyService.saveApplyInfo(applyInfo);
            finishDataRequestMap.put("responseType","SUCCESS");
        }else {
            //error
            finishDataRequestMap.put("responseType","ERROR");
        }
        return finishDataRequestMap;
    }

    /**
     * @Description //TODO
     * @Author Chen
     * @DateTime 2018/11/3
     * @Param
     * @Return
     */
    @PostMapping("/downloadApplyFile")
//    @PreAuthorize("hasRole('USER')")
    @Secured({"USER","ADMIN"})
    public @ResponseBody
    String downloadApplyFile(HttpServletResponse response,String applyId) {
        AccessUtils.getAccessAllow(response);
        // 文件名
        String fileName;
        String realPath;
        if(applyId.equals(null) || applyId ==""){
            ApplyInfo applyInfo = applyService.getApplyInfoDataByApplyType();
            fileName = applyInfo.getFileName();
            realPath = applyInfo.getFilePath();
        }else {
            ApplyInfo applyInfo = applyService.getApplyInfoDataByApplyId(applyId);
            fileName = applyInfo.getFileName();
            realPath = applyInfo.getFilePath();
        }
        if(fileName != null){
            File file = new File(realPath , fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");
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

    @RequestMapping(value = "/getApplyFile")
//    @PreAuthorize("hasRole('USER')")
    @Secured({"USER","ADMiN"})
    public @ResponseBody
    Object getApplyFile(HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        Map<String,ApplyInfo> applyInfoMap = new HashMap<>();
        ApplyInfo applyInfo = applyService.getApplyInfo();
        applyInfoMap.put("applyInfo",applyInfo);
        return applyInfoMap;
    }


    @RequestMapping(value = "/getAllApplyInfoForUser")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    Object getAllApplyInfoForUser (HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        List<ApplyInfo> applyInfos = applyService.getApplyInfosForUser();
        Map<String,List<ApplyInfo>> applyInfoMap = new HashMap<>();
        applyInfoMap.put("applyInfos",applyInfos);
        return applyInfoMap;
    }

    @RequestMapping(value = "/getAllApplyInfoForPf")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    Object getAllApplyInfoForPf (HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        List<ApplyInfo> applyInfos = applyService.getApplyInfosForPf();
        Map<String,List<ApplyInfo>> applyInfoMap = new HashMap<>();
        applyInfoMap.put("applyInfos",applyInfos);
        return applyInfoMap;
    }

    @RequestMapping(value = "/changeApplyType")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    Object changeApplyType (String applyId,String applyType,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        ApplyInfo applyInfo = applyService.getApplyInfoDataByApplyId(applyId);
        applyInfo.setApplyType(applyType);
        applyService.saveApplyInfo(applyInfo);
        Map<String,String> responseMap = new HashMap<>();
        responseMap.put("responseType","SUCCESS");
        return responseMap;
    }

    @RequestMapping(value = "/createPfInfo")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    Object createPfInfo (String applyUser,String selectedType,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        UserInfo userInfo = userService.findOneByUserName(applyUser);
        String pfName = professorService.createPfInfoAndSave(userInfo,selectedType);
        Map<String,String> responseMap = new HashMap<>();
        responseMap.put("pfName",pfName);
        return responseMap;
    }

    @RequestMapping(value = "/submitApplyFile")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    Object submitApplyFile (MultipartFile file,String applyType,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        Map<Object,Object> finishDataRequestMap = new HashMap<>();
        String localPath = "G:/idea/MyGitPros/SRARSystem/src/main/resources/static/assets/applyFile";
        String oldFileName = applyService.getApplyInfoDataByApplyType().getFileName();
        FileUtils.deleteFile(file, localPath, oldFileName);
        if (FileUtils.upload(file, localPath, file.getOriginalFilename())){
            //success
            applyService.submitApplyFile(applyType,file.getOriginalFilename(),localPath);
            finishDataRequestMap.put("responseType","SUCCESS");
        }else {
            //error
            finishDataRequestMap.put("responseType","ERROR");
        }
        return finishDataRequestMap;
    }

    @RequestMapping(value = "/ifApplyForUser")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody
    Object ifApplyForUser (String userName,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        Map<String,String> responseMap = new HashMap<>();
        ApplyInfo applyInfo = applyService.getApplyInfoByUserNameAndApplyTpye(userName);
        if(applyInfo !=null){
            responseMap.put("responseType","ERROR");
        }else {
            responseMap.put("responseType","SUCCESS");
        }
        return responseMap;
    }
}
