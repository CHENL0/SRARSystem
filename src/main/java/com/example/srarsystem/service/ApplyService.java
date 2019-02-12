package com.example.srarsystem.service;

import com.example.srarsystem.entity.ApplyInfo;

/**
 * @author Chen
 * @createTime 12 17:20
 * @description
 */
public interface ApplyService {
    ApplyInfo setApplyInfoData(ApplyInfo applyInfoData,String fileName,String filePath);
    void saveApplyInfo(ApplyInfo applyInfo);
    ApplyInfo getApplyInfoDataByApplyType();
    ApplyInfo getApplyInfo();
}
