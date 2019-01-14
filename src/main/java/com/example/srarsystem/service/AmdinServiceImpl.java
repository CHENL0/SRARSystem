package com.example.srarsystem.service;

import com.example.srarsystem.entity.AdminInfo;
import com.example.srarsystem.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Chen
 * @createTime 20181020 10:54
 * @description the impl of adminservice
 */
@Service
public class AmdinServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AmdinServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public boolean adminLogin(String adminName, String adminPassword) {
        AdminInfo adminInfo = adminRepository.findByAdminNameAndAdminPassword(adminName, adminPassword);
        if (adminInfo != null) {
            return true;
        }
        return false;
    }

    @Override
    public AdminInfo findOneByAdminName(String adminName) {
        return adminRepository.findOneByAdminName(adminName);
    }

}
