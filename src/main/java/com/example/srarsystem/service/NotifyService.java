package com.example.srarsystem.service;

import com.example.srarsystem.entity.NotifyInfo;

import java.util.List;

/**
 * @author Chen
 * @createTime 2019121 21:09
 * @description the service of notify
 */
public interface NotifyService {
    void setNotifyData (String notifyBy,String notifyFor,String notifyType,
                         String message,String notifyMain);

    List<NotifyInfo> getNotifyDataByUserName (String userName);

    NotifyInfo getNotifyInfoByNotifyId (String notifyId);

    void updateNotifyInfo (NotifyInfo notifyInfo);

    List<NotifyInfo> getNotifyListByUserNameAndNotifyId (String userName, String notifyId);

}
