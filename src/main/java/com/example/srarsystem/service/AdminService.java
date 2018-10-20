package com.example.srarsystem.service;

import org.springframework.stereotype.Service;

/**
 * @author Chen
 * @createTime 20181020 10:54
 * @description the interface of adminservice
 */
@Service
public interface AdminService {
    boolean adminLogin (String adminName,String adminPassword);
    String getAdminIdByAdminName (String adminName);
}
