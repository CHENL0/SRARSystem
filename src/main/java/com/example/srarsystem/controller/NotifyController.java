package com.example.srarsystem.controller;

import com.example.srarsystem.commons.AccessUtils;
import com.example.srarsystem.entity.NotifyInfo;
import com.example.srarsystem.service.NotifyService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chen
 * @createTime 2019121 21:06
 * @description the controller of notify
 */
@Controller
@RequestMapping(value = "/notify")
public class NotifyController {

    private final NotifyService notifyService;

    public NotifyController(NotifyService notifyService) {
        this.notifyService = notifyService;
    }

    @RequestMapping(value = "/setNotifyDataForUser")
//    @PreAuthorize("hasRole('USER') AND hasRole('ADMON') AND hasRole('PROFESSOR')")
    @Secured({ "USER", "ADMiN","PROFESSOR"})
    public @ResponseBody
    Object setNotifyDataForUser (String notifyBy,String notifyFor,String notifyType,
                          int notifyStatus,String notifyMain,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        String message = "";
        if(notifyType.equals("Project")){
            if(notifyStatus == 2){
                message = "   Hi, "+ notifyFor + ", you are luck that your project '"+ notifyMain+"' was passed by "+ notifyBy
                        +", best regard!!";

            }else if(notifyStatus == 3){
                message = "   Hi, "+ notifyFor + ", i sorry to tell you that your project '"+ notifyMain+"' was rejected by "+ notifyBy
                        +", best regard !!";
            }
        }else if(notifyType.equals("Task")){
            if(notifyStatus == 2){
                message = "   Hi, "+ notifyFor + ", you have a new task ' "+ notifyMain + " ' by "+ notifyBy
                        +", best regard !!";
            }else if(notifyStatus == 3){
                message = "   Hi, "+ notifyFor + ", i sorry to tell you that your task  was unfinished "
                        +", best regard !!";
            }else if(notifyStatus == 4){
                message = "   Hi, "+ notifyFor + ", i sorry to tell you that your task  was timeout "
                        +", best regard !!";
            }
        }else if(notifyType.equals("Apply")){
            if(notifyStatus == 2){
                message = "   Hi, "+ notifyFor + ", you are luck that your apply has passed by "+ notifyBy
                        +" and your account name is <"+ notifyMain +">,the password as your current account, best regard !!";
            }else if(notifyStatus == 3){
                message = "   Hi, "+ notifyFor + ", i sorry to tell you that your apply has reject by "+ notifyBy
                        +" and your can continue to improve your message, best regard !!";
            }
        }
        notifyService.setNotifyDataForUser(notifyBy,notifyFor,notifyType,message, notifyMain);
        return null;
    }

    @RequestMapping(value = "/setNotifyDataForAudit")
    public @ResponseBody
    Object setNotifyDataForAudit (String notifyBy,String notifyFor,String notifyType,
                                 int notifyStatus,String notifyMain,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        String message = "";
        if(notifyType.equals("Project")){
            if(notifyStatus == 2){
                message = "   Hi, "+ notifyFor + ", you have a new project '"+ notifyMain+"'  was applied for "+ notifyBy
                        +", best regard !!";

            }
        }else if(notifyType.equals("Task")){
            if(notifyStatus == 2){
                message = "   Hi, "+ notifyFor + ", you have a task "+ notifyMain +" was committed by "+ notifyBy
                        +", best regard !!";
            }else if(notifyStatus == 3){
                message = "   Hi, "+ notifyFor + ", i sorry to tell you that your task "+ notifyMain +" was unfinished by "+ notifyBy
                        +", best regard !!";
            }else if(notifyStatus == 4){
                message = "   Hi, "+ notifyFor + ", i sorry to tell you that your task "+ notifyMain +" was timeout by "+ notifyBy
                        +", best regard !!";
            }
            notifyService.setNotifyDataForUser(notifyBy,notifyFor,notifyType,message, notifyMain);
        }
        return null;
    }

    @RequestMapping(value = "/getNotifyDataForUser")
//    @PreAuthorize("hasRole('USER')")
    @Secured({ "USER", "ADMiN","PROFESSOR"})
    public @ResponseBody
    Object getNotifyDataForUser (String userName,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        List<NotifyInfo> notifyInfoList = notifyService.getNotifyDataByUserNameAndDelFlag(userName);
        Map<String,List<NotifyInfo>> notifyInfoListMap= new HashMap<>();
        notifyInfoListMap.put("notifyInfoList",notifyInfoList);
        return notifyInfoListMap;
    }

    @RequestMapping(value = "/getNotifyDataForAudit")
//    @PreAuthorize("hasRole('USER') AND hasRole('ADMON') AND hasRole('PROFESSOR')")
    @Secured({ "USER", "ADMiN","PROFESSOR"})
    public @ResponseBody
    Object getNotifyDataForAudit (String userName,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        List<NotifyInfo> notifyInfoList = notifyService.getNotifyDataByUserNameAndDelFlagAudit(userName);
        Map<String,List<NotifyInfo>> notifyInfoListMap= new HashMap<>();
        notifyInfoListMap.put("notifyInfoList",notifyInfoList);
        return notifyInfoListMap;
    }



    @RequestMapping(value = "/changeNotifyInfo")
//    @PreAuthorize("hasRole('USER') AND hasRole('ADMON') AND hasRole('PROFESSOR')")
    @Secured({ "USER", "ADMiN","PROFESSOR"})
    public @ResponseBody
    void changeNotifyInfo (String notifyId,int notifyStatus,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        NotifyInfo notifyInfo = notifyService.getNotifyInfoByNotifyId(notifyId);
        notifyInfo.setNotifyStatus(notifyStatus);
        notifyService.updateNotifyInfo(notifyInfo);
    }

    @RequestMapping(value = "/getOneNotifyData")
//    @PreAuthorize("hasRole('USER') AND hasRole('ADMiN') AND hasRole('PROFESSOR')")
    @Secured({ "USER", "ADMiN","PROFESSOR"})
    public @ResponseBody
    Object getOneNotifyData (String notifyId,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        NotifyInfo notifyInfo = notifyService.getNotifyInfoByNotifyId(notifyId);
        Map<String,NotifyInfo> notifyInfoMap= new HashMap<>();
        notifyInfoMap.put("notifyInfo",notifyInfo);
        return notifyInfoMap;
    }

}
