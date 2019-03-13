package com.example.srarsystem.service;

import com.example.srarsystem.commons.UUIDUtils;
import com.example.srarsystem.entity.NotifyInfo;
import com.example.srarsystem.repository.NotifyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Chen
 * @createTime 2019121 21:09
 * @description the impl of  notifyservice
 */
@Service
public class NotifyServiceImpl implements NotifyService{

    private final NotifyRepository notifyRepository;

    public NotifyServiceImpl(NotifyRepository notifyRepository) {
        this.notifyRepository = notifyRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setNotifyDataForUser(String notifyBy, String notifyFor, String notifyType,
                               String message,String notifyMain) {
        NotifyInfo notifyInfo = new NotifyInfo();
        String notifyDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        notifyInfo.setNotifyId(UUIDUtils.getUUID());
        notifyInfo.setNotifyFor(notifyFor);
        notifyInfo.setNotifyBy(notifyBy);
        notifyInfo.setNotifyStatus(1);
        notifyInfo.setNotifyMessage(message);
        notifyInfo.setNotifyMain(notifyMain);
        notifyInfo.setNotifyType(notifyType);
        notifyInfo.setNotifyDate(notifyDate);
        notifyRepository.save(notifyInfo);
    }

    @Override
    public List<NotifyInfo> getNotifyDataByUserNameAndDelFlag(String userName) {
        List<NotifyInfo> notifyInfoList = notifyRepository.findByNotifyForAndDelFlag(userName);
        return notifyInfoList;
    }

    @Override
    public List<NotifyInfo> getNotifyDataByUserNameAndDelFlagAudit(String userName) {
        List<NotifyInfo> notifyInfoList = notifyRepository.findByNotifyForAndDelFlagAudit(userName,0);
        return notifyInfoList;
    }

    @Override
    public NotifyInfo getNotifyInfoByNotifyId(String notifyId) {
        return notifyRepository.getOne(notifyId);
    }

    @Override
    public void updateNotifyInfo(NotifyInfo notifyInfo) {
        notifyRepository.save(notifyInfo);
    }

    @Override
    public List<NotifyInfo> getNotifyListByUserNameAndNotifyId(String userName, String notifyId) {
        return notifyRepository.findAllByNotifyForAndNotifyStatus(userName, notifyId);
    }

//    @Override
//    public void deleteNotifyDataByTaskIdForUser(String notifyId) {
//        NotifyInfo notifyInfo = notifyRepository.getOne(notifyId);
//        notifyInfo.setDelFlag(1);
//        notifyRepository.save(notifyInfo);
//    }
}
