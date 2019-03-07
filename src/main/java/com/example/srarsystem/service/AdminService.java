package com.example.srarsystem.service;

import com.example.srarsystem.entity.AdminInfo;
import org.springframework.stereotype.Service;

/**
 * @author Chen
 * @createTime 20181020 10:54
 * @description the interface of adminservice
 */
public interface AdminService {
    boolean adminLogin(String adminName, String adminPassword);
    void  saveAdmin(String adminName, String adminPassword);
    AdminInfo findOneByAdminName(String adminName);
}
