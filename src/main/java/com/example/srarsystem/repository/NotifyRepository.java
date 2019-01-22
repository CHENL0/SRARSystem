package com.example.srarsystem.repository;

import com.example.srarsystem.entity.NotifyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Chen
 * @createTime 2019121 21:07
 * @description the respository of notify
 */
public interface NotifyRepository extends JpaRepository<NotifyInfo, String> {
    List<NotifyInfo> findByNotifyFor (String userName);
    List<NotifyInfo> findAllByNotifyForAndNotifyStatus(String userName,String notifyStatus);
}
