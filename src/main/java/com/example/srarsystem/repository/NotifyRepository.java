package com.example.srarsystem.repository;

import com.example.srarsystem.entity.NotifyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Chen
 * @createTime 2019121 21:07
 * @description the respository of notify
 */
public interface NotifyRepository extends JpaRepository<NotifyInfo, String> {
    @Query("SELECT n FROM NotifyInfo n where n.notifyFor = ?1 and n.notifyStatus in ('1','2') ")
    List<NotifyInfo> findByNotifyForAndDelFlag (String userName);
    List<NotifyInfo> findByNotifyForAndDelFlagAudit (String userName,int delFlagAudit);
    List<NotifyInfo> findAllByNotifyForAndNotifyStatus(String userName,String notifyStatus);
}
