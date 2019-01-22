package com.example.srarsystem.controller;

import com.example.srarsystem.commons.AccessUtils;
import com.example.srarsystem.entity.NotifyInfo;
import com.example.srarsystem.service.NotifyService;
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

    @RequestMapping(value = "/setNotifyData")
    public @ResponseBody
    Object setNotifyData (String notifyBy,String notifyFor,String notifyType,
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
            notifyService.setNotifyData(notifyBy,notifyFor,notifyType,message, notifyMain);
        }

        return null;
    }

    @RequestMapping(value = "/getNotifyData")
    public @ResponseBody
    Object getNotifyData (String userName,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        List<NotifyInfo> notifyInfoList = notifyService.getNotifyDataByUserName(userName);
        Map<String,List<NotifyInfo>> notifyInfoListMap= new HashMap<>();
        notifyInfoListMap.put("notifyInfoList",notifyInfoList);
        return notifyInfoListMap;
    }

//    @RequestMapping(value = "/getNotifyListByStatus")
//    public @ResponseBody
//    Object getNotifyListByStatus (String userName,String notifyStatus, HttpServletResponse response){
//        AccessUtils.getAccessAllow(response);
//        List<NotifyInfo> oneStatusNotifyInfoList = notifyService.getNotifyListByUserNameAndNotifyId(userName,notifyStatus);
//        Map<String,List<NotifyInfo>> oneStatusNotifyInfoListMap= new HashMap<>();
//        oneStatusNotifyInfoListMap.put("oneStatusNotifyInfoList",oneStatusNotifyInfoList);
//        return oneStatusNotifyInfoListMap;
//    }


    @RequestMapping(value = "/changeNotifyInfo")
    public @ResponseBody
    void changeNotifyInfo (String notifyId,int notifyStatus,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        NotifyInfo notifyInfo = notifyService.getNotifyInfoByNotifyId(notifyId);
        notifyInfo.setNotifyStatus(notifyStatus);
        notifyService.updateNotifyInfo(notifyInfo);
    }

    @RequestMapping(value = "/getOneNotifyData")
    public @ResponseBody
    Object getOneNotifyData (String notifyId,HttpServletResponse response){
        AccessUtils.getAccessAllow(response);
        NotifyInfo notifyInfo = notifyService.getNotifyInfoByNotifyId(notifyId);
        Map<String,NotifyInfo> notifyInfoMap= new HashMap<>();
        notifyInfoMap.put("notifyInfo",notifyInfo);
        return notifyInfoMap;
    }

}
