package com.example.srarsystem.service;

import com.example.srarsystem.commons.UUIDUtils;
import com.example.srarsystem.entity.ApplyInfo;
import com.example.srarsystem.repository.ApplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Chen
 * @createTime 12 17:21
 * @description
 */
@Service
public class ApplyServiceImpl implements ApplyService {

    private final ApplyRepository applyRepository;

    public ApplyServiceImpl(ApplyRepository applyRepository) {
        this.applyRepository = applyRepository;
    }

    @Override
    public ApplyInfo getApplyInfoByUserNameAndApplyTpye(String userName) {
        ApplyInfo applyInfo = applyRepository.findApplyInfoByApplyUser(userName);
        return applyInfo;
    }

    @Override
    public ApplyInfo setApplyInfoData(ApplyInfo applyInfoData, String fileName, String filePath) {
        applyInfoData.setApplyId(UUIDUtils.getUUID());
        applyInfoData.setApplyType("1");
        applyInfoData.setFileName(fileName);
        applyInfoData.setFilePath(filePath);
        return applyInfoData;
    }

    @Override
    public void submitApplyFile(String applyType, String fileName, String filePath) {
        ApplyInfo applyInfo = applyRepository.findApplyInfoByApplyType(applyType);
        applyInfo.setFilePath(filePath);
        applyInfo.setFileName(fileName);
        applyRepository.save(applyInfo);
    }

    @Override
    public void saveApplyInfo(ApplyInfo applyInfo) {
        applyRepository.save(applyInfo);
    }

    @Override
    public ApplyInfo getApplyInfoDataByApplyType() {
        return applyRepository.findApplyInfoByApplyType("applyFile");
    }

    @Override
    public ApplyInfo getApplyInfoDataByApplyId(String applyId) {
        return applyRepository.findByApplyId(applyId);
    }

    @Override
    public ApplyInfo getApplyInfo() {
        return applyRepository.findByApplyId("01");
    }

    @Override
    public List<ApplyInfo> getApplyInfos() {
        return applyRepository.findApplyInfos();
    }
}
