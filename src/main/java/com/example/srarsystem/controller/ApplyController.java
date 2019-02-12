package com.example.srarsystem.controller;

import com.example.srarsystem.commons.AccessUtils;
import com.example.srarsystem.commons.FileUtils;
import com.example.srarsystem.entity.ApplyInfo;
import com.example.srarsystem.service.ApplyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
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

    @RequestMapping(value = "/submitApplyInfoData")
    public @ResponseBody
    Object submitApplyInfoData(MultipartFile file, String applyInfoData
            , HttpServletResponse response) throws IOException {
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
    @PostMapping("/download")
    public @ResponseBody
    String downloadFile(HttpServletResponse response) {
        AccessUtils.getAccessAllow(response);
        // 文件名
        ApplyInfo applyInfo = applyService.getApplyInfoDataByApplyType();
        String fileName = applyInfo.getFileName();
        String realPath = applyInfo.getFilePath();
        if (fileName != null) {
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
    public @ResponseBody
    Object getApplyFile(HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        Map<String,ApplyInfo> applyInfoMap = new HashMap<>();
        ApplyInfo applyInfo = applyService.getApplyInfo();
        applyInfoMap.put("applyInfo",applyInfo);
        return applyInfoMap;
    }
}
