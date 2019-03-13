package com.example.srarsystem.repository;

import com.example.srarsystem.entity.ApplyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Chen
 * @createTime 12 17:20
 * @description
 */
public interface ApplyRepository extends JpaRepository<ApplyInfo,String> {
    ApplyInfo findApplyInfoByApplyType(String applyType);

    ApplyInfo findByApplyId(String applyId);

    @Query("SELECT a FROM ApplyInfo a where  a.applyType in ('1','2','3') and a.applyUser LIKE CONCAT('%',:applyUser,'%')")
    List<ApplyInfo> findApplyInfosForUser(@Param("applyUser")String applyUser);

    @Query("SELECT a FROM ApplyInfo a where  a.applyType in ('1','4','5') and a.applyUser LIKE CONCAT('%',:applyUser,'%')")
    List<ApplyInfo> findApplyInfosForPf(@Param("applyUser")String applyUser);

    @Query("SELECT a FROM ApplyInfo a where a.applyUser=?1 and a.applyType in ('1','2') ")
    ApplyInfo findApplyInfoByApplyUser(String applyUser);
}
