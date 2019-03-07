package com.example.srarsystem.repository;

import com.example.srarsystem.entity.ApplyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Chen
 * @createTime 12 17:20
 * @description
 */
public interface ApplyRepository extends JpaRepository<ApplyInfo,String> {
    ApplyInfo findApplyInfoByApplyType(String applyType);
    ApplyInfo findByApplyId(String applyId);
    @Query("SELECT a FROM ApplyInfo a where  a.applyType in ('1','2','3') ")
    List<ApplyInfo> findApplyInfos();
    @Query("SELECT a FROM ApplyInfo a where a.applyUser=?1 and a.applyType in ('1','2') ")
    ApplyInfo findApplyInfoByApplyUser(String applyUser);
}
