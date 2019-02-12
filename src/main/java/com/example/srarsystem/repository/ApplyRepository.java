package com.example.srarsystem.repository;

import com.example.srarsystem.entity.ApplyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Chen
 * @createTime 12 17:20
 * @description
 */
public interface ApplyRepository extends JpaRepository<ApplyInfo,String> {
    ApplyInfo findApplyInfoByApplyType(String applyType);
    ApplyInfo findByApplyId(String applyId);
}
