package com.example.srarsystem.service;

import com.example.srarsystem.entity.ApplyInfo;

import java.util.List;

/**
 * @author Chen
 * @createTime 12 17:20
 * @description
 */
public interface ApplyService {
    ApplyInfo getApplyInfoByUserNameAndApplyTpye(String userName);
    ApplyInfo setApplyInfoData(ApplyInfo applyInfoData,String fileName,String filePath);
    void submitApplyFile(String applyType,String fileName,String filePath);
    void saveApplyInfo(ApplyInfo applyInfo);
    ApplyInfo getApplyInfoDataByApplyType();
    ApplyInfo getApplyInfoDataByApplyId(String applyId);
    ApplyInfo getApplyInfo();
    List<ApplyInfo> getApplyInfosForUser();
    List<ApplyInfo> getApplyInfosForPf();
}
