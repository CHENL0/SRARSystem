package com.example.srarsystem.service;

import com.example.srarsystem.commons.UUIDUtils;
import com.example.srarsystem.entity.ApplyInfo;
import com.example.srarsystem.repository.ApplyRepository;
import org.springframework.stereotype.Service;

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
    public ApplyInfo setApplyInfoData(ApplyInfo applyInfoData, String fileName, String filePath) {
        applyInfoData.setApplyId(UUIDUtils.getUUID());
        applyInfoData.setFileName(fileName);
        applyInfoData.setFilePath(filePath);
        return applyInfoData;
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
    public ApplyInfo getApplyInfo() {
        return applyRepository.findByApplyId("01");
    }
}
